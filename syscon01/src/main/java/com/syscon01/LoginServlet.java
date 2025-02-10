package com.syscon01;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String userId = request.getParameter("userId");
        boolean userExists = false;

        // データベース接続情報
        String url = "jdbc:mysql://localhost:3306/account"; // データベース名を入力してください
        String username = "root"; // ユーザー名を入力してください
        String password = "password"; // パスワードを入力してください

        try {
            // MySQL JDBC ドライバをロード
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);

            // SQLクエリでユーザーIDを確認
            String sql = "SELECT * FROM ユーザー WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            // ユーザーが存在するかチェック
            if (rs.next()) {
                userExists = true;
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 存在する場合はmainPage.jspへ、存在しない場合はerrorPage.jspへ
        if (userExists) {
            HttpSession session = request.getSession();
            session.setAttribute("userId", userId);
            response.sendRedirect("mainPage.jsp");
        } else {
            response.sendRedirect("errorPage.jsp");
        }
    }
}
