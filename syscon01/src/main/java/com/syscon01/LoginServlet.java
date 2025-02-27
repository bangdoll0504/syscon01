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

import com.utils.PasswordUtil;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userId = request.getParameter("userId");
        String userPass = request.getParameter("password");
        boolean userExists = false;

        // DB接続情報（適宜変更してください）
        String url = "jdbc:mysql://localhost:3306/syskadai";
        String dbUser = "root";
        String dbPass = "password";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, dbUser, dbPass);
            String sql = "SELECT * FROM employee WHERE empid = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPass = rs.getString("emppassword_hash");
                boolean flag = rs.getBoolean("flag");
                int userRole = rs.getInt("emprole"); // 追加：ユーザーロールの取得

                if (flag) { // 利用者権限の場合：入力されたパスワードをハッシュ化して比較
                    String hashedInput = PasswordUtil.hashPassword(userPass);
                    System.out.println(storedPass);
                    System.out.println(hashedInput);
                    if (hashedInput.equals(storedPass)) {
                        userExists = true;
                    }
                } else { // 管理者権限の場合：直接比較
                    if (userPass.equals(storedPass)) {
                        userExists = true;
                    }
                }
                if (userExists) {
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", userId);
                    session.setAttribute("userRole", userRole); // 追加：ユーザーロールをセッションに保存
                }
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(userExists);
        if (userExists) {
            response.sendRedirect("mainPage.jsp");
        } else {
            response.sendRedirect("errorPage.jsp");
        }
    }
}
