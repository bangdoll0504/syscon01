package com.syscon01;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PatiRegister")
public class PatiRegister extends HttpServlet {
    
    // ※実際の環境に合わせてDB接続情報を設定してください
    private static final String DB_URL = "jdbc:mysql://localhost:3306/syskadai";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "password";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action == null) {
            response.sendRedirect("mainPage.jsp");
            return;
        }
        try {
            if("confirm".equals(action)) {
                handleConfirm(request, response);
            } else if("register".equals(action)) {
                handleRegister(request, response);
            } else {
                response.sendRedirect("mainPage.jsp");
            }
        } catch(Exception e) {
            throw new ServletException(e);
        }
    }
    
    // 入力内容を確認画面へ渡す処理
    private void handleConfirm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("patid", request.getParameter("patid"));
        request.setAttribute("patfname", request.getParameter("patfname"));
        request.setAttribute("patlname", request.getParameter("patlname"));
        request.setAttribute("hokenmei", request.getParameter("hokenmei"));
        request.setAttribute("hokenexp", request.getParameter("hokenexp"));
        RequestDispatcher rd = request.getRequestDispatcher("patrconfirm.jsp");
        rd.forward(request, response);
    }
    
    // DBへの登録処理
    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        String patid = request.getParameter("patid");
        String patfname = request.getParameter("patfname");
        String patlname = request.getParameter("patlname");
        String hokenmei = request.getParameter("hokenmei");
        String hokenexp = request.getParameter("hokenexp"); // 日付形式 (yyyy-MM-dd)
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            String sql = "INSERT INTO patient (patid, patfname, patlname, hokenmei, hokenexp) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, patid);
            pstmt.setString(2, patfname);
            pstmt.setString(3, patlname);
            pstmt.setString(4, hokenmei);
            pstmt.setDate(5, Date.valueOf(hokenexp)); // 文字列から java.sql.Date へ変換
            int count = pstmt.executeUpdate();
            if(count > 0) {
                request.setAttribute("message", "登録が完了しました");
            } else {
                request.setAttribute("message", "登録に失敗しました");
            }
            RequestDispatcher rd = request.getRequestDispatcher("patrsuccess.jsp");
            rd.forward(request, response);
        } finally {
            if(pstmt != null) try { pstmt.close(); } catch(Exception ignore) {}
            if(conn != null) try { conn.close(); } catch(Exception ignore) {}
        }
    }
}
