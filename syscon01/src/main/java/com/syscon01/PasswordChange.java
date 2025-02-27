package com.syscon01;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.utils.PasswordUtil;

@WebServlet("/PasswordChange")
public class PasswordChange extends HttpServlet {

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
             } else if("change".equals(action)) {
                 handleChange(request, response);
             } else if("キャンセル".equals(action)) {
                 response.sendRedirect("mainPage.jsp");
             } else if("編集".equals(action)) {
                 // 編集の場合は入力画面に戻す
                 RequestDispatcher rd = request.getRequestDispatcher("changePassword.jsp");
                 rd.forward(request, response);
             } else {
                 response.sendRedirect("mainPage.jsp");
             }
         } catch(Exception e) {
             throw new ServletException(e);
         }
    }

    // 「確認」ボタン処理：入力されたパスワードが一致しているかチェックし、
    // 一致すれば確認画面 (confirmPassword.jsp) にフォワード
    private void handleConfirm(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
         String newPassword = request.getParameter("newPassword");
         String confirmPassword = request.getParameter("confirmPassword");
         if(newPassword == null || confirmPassword == null || !newPassword.equals(confirmPassword)) {
             request.setAttribute("errorMsg", "パスワードが一致しません。");
             RequestDispatcher rd = request.getRequestDispatcher("changePassword.jsp");
             rd.forward(request, response);
             return;
         }
         // パスワードが一致している場合は確認画面へ
         RequestDispatcher rd = request.getRequestDispatcher("confirmPassword.jsp");
         rd.forward(request, response);
    }

    // 「変更」ボタン処理：ユーザーのロールに応じてパスワードをハッシュ化し、DBを更新
    private void handleChange(HttpServletRequest request, HttpServletResponse response)
         throws Exception {
         // セッションからログイン中ユーザーの情報を取得
         HttpSession session = request.getSession(false);
         if(session == null || session.getAttribute("userId") == null) {
             response.sendRedirect("login.jsp");
             return;
         }
         String userId = (String) session.getAttribute("userId");
         int userRole = (Integer) session.getAttribute("userRole"); // role=0なら平文、それ以外ならハッシュ化

         String newPassword = request.getParameter("newPassword");
         boolean hashFlag = false;
         String passwordToStore = newPassword;
         if(userRole != 0) {
             passwordToStore =  PasswordUtil.hashPassword(newPassword);
             hashFlag = true;
         }
         
         // DB更新処理：userテーブルの該当ユーザーのパスワードとhashFlagを更新
         Connection conn = null;
         PreparedStatement pstmt = null;
         try {
             Class.forName("com.mysql.cj.jdbc.Driver");
             conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             String sql = "UPDATE employee SET emppassword_hash = ?, flag = ? WHERE empid = ?";
             pstmt = conn.prepareStatement(sql);
             pstmt.setString(1, passwordToStore);
             pstmt.setBoolean(2, hashFlag);
             pstmt.setString(3, userId);
             int count = pstmt.executeUpdate();
             if(count > 0) {
                 request.setAttribute("message", "完了しました");
             } else {
                 request.setAttribute("message", "更新に失敗しました");
             }
             RequestDispatcher rd = request.getRequestDispatcher("completePassword.jsp");
             rd.forward(request, response);
         } finally {
             if(pstmt != null) try { pstmt.close(); } catch(Exception ignore){}
             if(conn != null) try { conn.close(); } catch(Exception ignore){}
         }
    }
    
}
