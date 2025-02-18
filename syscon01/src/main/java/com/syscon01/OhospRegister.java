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

@WebServlet("/OhospRegisterServlet")
public class OhospRegister extends HttpServlet {

    // ※実際の環境に合わせてDB_URL、DB_USER、DB_PASSを設定してください
    private static final String DB_URL = "jdbc:mysql://localhost:3306/syskadai";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "password";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect("mainPage.jsp");
            return;
        }
        try {
            if (action.equals("confirm")) {
                handleConfirm(request, response);
            } else if (action.equals("register")) {
                handleRegister(request, response);
            } else {
                response.sendRedirect("mainPage.jsp");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // 入力内容を確認画面に渡す処理
    private void handleConfirm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("tabyouinid", request.getParameter("tabyouinid"));
        request.setAttribute("tabyouinmei", request.getParameter("tabyouinmei"));
        request.setAttribute("tabyouinaddress", request.getParameter("tabyouinaddress"));
        request.setAttribute("tabyouintel", request.getParameter("tabyouintel"));
        request.setAttribute("tabyouinshihonkin", request.getParameter("tabyouinshihonkin"));
        request.setAttribute("kyukyu", request.getParameter("kyukyu"));
        RequestDispatcher rd = request.getRequestDispatcher("ohospconfirm.jsp");
        rd.forward(request, response);
    }

    // DBへの登録処理
    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        String tabyouinid = request.getParameter("tabyouinid");
        String tabyouinmei = request.getParameter("tabyouinmei");
        String tabyouinaddress = request.getParameter("tabyouinaddress");
        String tabyouintel = request.getParameter("tabyouintel");
        String tabyouinshihonkinStr = request.getParameter("tabyouinshihonkin");
        String kyukyuStr = request.getParameter("kyukyu");
        int tabyouinshihonkin = 0;
        int kyukyu = 0;
        try {
            tabyouinshihonkin = Integer.parseInt(tabyouinshihonkinStr);
        } catch (NumberFormatException e) {
            // 必要に応じてエラーハンドリング
        }
        try {
            kyukyu = Integer.parseInt(kyukyuStr);
        } catch (NumberFormatException e) {
            // 必要に応じてエラーハンドリング
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            String sql = "INSERT INTO tabyouin (tabyouinid, tabyouinmei, tabyouinaddress, tabyouintel, tabyouinshihonkin, kyukyu) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tabyouinid);
            pstmt.setString(2, tabyouinmei);
            pstmt.setString(3, tabyouinaddress);
            pstmt.setString(4, tabyouintel);
            pstmt.setInt(5, tabyouinshihonkin);
            pstmt.setInt(6, kyukyu);
            int count = pstmt.executeUpdate();
            if (count > 0) {
                request.setAttribute("message", "登録が完了しました");
            } else {
                request.setAttribute("message", "登録に失敗しました");
            }
            RequestDispatcher rd = request.getRequestDispatcher("ohostpcomplete.jsp");
            rd.forward(request, response);
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch (Exception ignore) {}
            if (conn != null) try { conn.close(); } catch (Exception ignore) {}
        }
    }
}
