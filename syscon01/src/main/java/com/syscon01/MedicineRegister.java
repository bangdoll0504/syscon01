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

@WebServlet("/MedicineRegister")
public class MedicineRegister extends HttpServlet {

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
                // 入力内容を確認画面へ渡す
            	String medicineId = request.getParameter("medicineId");
            	String medicineName = request.getParameter("medicineName");
            	String unit = request.getParameter("unit");

            	// ここでリクエスト属性にセット
            	request.setAttribute("medicineId", medicineId);
            	request.setAttribute("medicineName", medicineName);
            	request.setAttribute("unit", unit);

                RequestDispatcher rd = request.getRequestDispatcher("medconfirm.jsp");
                rd.forward(request, response);
            } else if("登録".equals(action)) {
                handleRegister(request, response);
            } else if("修正".equals(action)) {
                // 修正の場合は、入力画面へ戻す（値は再入力またはリクエスト属性で再表示）
                RequestDispatcher rd = request.getRequestDispatcher("medreg.jsp");
                rd.forward(request, response);
            } else if("キャンセル".equals(action)) {
                response.sendRedirect("mainPage.jsp");
            } else {
                response.sendRedirect("mainPage.jsp");
            }
        } catch(Exception e) {
            throw new ServletException(e);
        }
    }
    
    // DBへの登録処理
    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException, ClassNotFoundException, SQLException {
        String medicineId = request.getParameter("medicineId");
        String medicineName = request.getParameter("medicineName");
        String unit = request.getParameter("unit");
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            String sql = "INSERT INTO medicine (medicineid, medicinename, unit) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, medicineId);
            pstmt.setString(2, medicineName);
            pstmt.setString(3, unit);
            int count = pstmt.executeUpdate();
            if(count > 0) {
                request.setAttribute("message", "登録が完了しました");
            } else {
                request.setAttribute("message", "登録に失敗しました");
            }
            RequestDispatcher rd = request.getRequestDispatcher("medcomplete.jsp");
            rd.forward(request, response);
        } finally {
            if(pstmt != null) try { pstmt.close(); } catch(Exception ignore) {}
            if(conn != null) try { conn.close(); } catch(Exception ignore) {}
        }
    }
}
