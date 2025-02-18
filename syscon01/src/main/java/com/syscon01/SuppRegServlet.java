package com.syscon01;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SuppRegServlet")
public class SuppRegServlet extends HttpServlet {

    // ※実際の環境に合わせてDB_URL、DB_USER、DB_PASSを設定してください
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
            if(action.equals("confirm")) {
                handleConfirm(request, response);
            } else if(action.equals("register")) {
                handleRegister(request, response);
            } else {
                response.sendRedirect("mainPage.jsp");
            }
        } catch(Exception e) {
            throw new ServletException(e);
        }
    }

    // 入力内容を確認画面に渡す処理
    private void handleConfirm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("shiireid", request.getParameter("shiireid"));
        request.setAttribute("shiiremei", request.getParameter("shiiremei"));
        request.setAttribute("shiireaddress", request.getParameter("shiireaddress"));
        request.setAttribute("shiiretel", request.getParameter("shiiretel"));
        request.setAttribute("shihonkin", request.getParameter("shihonkin"));
        request.setAttribute("nouki", request.getParameter("nouki"));
        RequestDispatcher rd = request.getRequestDispatcher("suppconfirm.jsp");
        rd.forward(request, response);
    }

    // DBへの登録処理
    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        String shiireid = request.getParameter("shiireid");
        String shiiremei = request.getParameter("shiiremei");
        String shiireaddress = request.getParameter("shiireaddress");
        String shiiretel = request.getParameter("shiiretel");
        String shihonkinStr = request.getParameter("shihonkin");
        String noukiStr = request.getParameter("nouki");
        int shihonkin = 0;
        int nouki = 0;
        try {
            shihonkin = Integer.parseInt(shihonkinStr);
        } catch(NumberFormatException e) {
            // 必要に応じてエラーハンドリング
        }
        try {
            nouki = Integer.parseInt(noukiStr);
        } catch(NumberFormatException e) {
            // 必要に応じてエラーハンドリング
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            String sql = "INSERT INTO shiiregyosha (shiireid, shiiremei, shiireaddress, shiiretel, shihonkin, nouki) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shiireid);
            pstmt.setString(2, shiiremei);
            pstmt.setString(3, shiireaddress);
            pstmt.setString(4, shiiretel);
            pstmt.setInt(5, shihonkin);
            pstmt.setInt(6, nouki);
            int count = pstmt.executeUpdate();
            if(count > 0) {
                request.setAttribute("message", "登録が完了しました");
            } else {
                request.setAttribute("message", "登録に失敗しました");
            }
            RequestDispatcher rd = request.getRequestDispatcher("suppcomplete.jsp");
            rd.forward(request, response);
        } finally {
            if(pstmt != null) try { pstmt.close(); } catch(Exception ignore){}
            if(conn != null) try { conn.close(); } catch(Exception ignore){}
        }
    }
}
