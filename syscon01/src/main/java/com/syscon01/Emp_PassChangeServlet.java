package com.syscon01;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EmployeePasswordChangeServlet")
public class Emp_PassChangeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // データベース接続情報（環境に合わせて変更してください）
    private static final String DB_URL = "jdbc:mysql://localhost:3306/syskadai";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // actionパラメータにより処理を分岐
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect("mainPage.jsp");
            return;
        }
        
        switch(action) {
            case "checkEmployee":		// 従業員IDの存在チェック
                checkEmployee(request, response);
                break;
            case "validatePasswords":	// 入力された2つのパスワードの比較
                validatePasswords(request, response);
                break;
            case "updatePassword":		// パスワードの更新
                updatePassword(request, response);
                break;
            default:
                response.sendRedirect("mainPage.jsp");
                break;
        }
    }
    
    // ① 従業員ID入力後、従業員が存在するかをチェック
    private void checkEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeId = request.getParameter("employeeId");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "SELECT emprole FROM employee WHERE empid = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, employeeId);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            String role = rs.getString("emprole");
                            // 従業員が見つかったので、パスワード入力画面へフォワード
                            request.setAttribute("employeeId", employeeId);
                            request.setAttribute("role", role);
                            request.getRequestDispatcher("emp_p_upd_input.jsp").forward(request, response);
                        } else {
                            // 該当の従業員が存在しない場合、エラーメッセージをセットして入力画面へ戻す
                            request.setAttribute("errorMsg", "従業員が見つかりません");
                            request.getRequestDispatcher("emp_p_input.jsp").forward(request, response);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ServletException("従業員チェック中にエラーが発生しました", e);
        }
    }
    
    // ② 2つのパスワード入力欄の値が一致するか検証
    private void validatePasswords(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeId = request.getParameter("employeeId");
        String role = request.getParameter("role");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        
        if (password1 == null || password2 == null || !password1.equals(password2)) {
            request.setAttribute("errorMsg", "パスワードが一致しません。再入力してください。");
            request.setAttribute("employeeId", employeeId);
            request.setAttribute("role", role);
            request.getRequestDispatcher("emp_p_input.jsp").forward(request, response);
        } else {
            // 一致している場合は最終確認画面へ
        	System.out.println("validatePasswords");
        	System.out.println(password1);
            request.setAttribute("employeeId", employeeId);
            request.setAttribute("password", password1);
            request.setAttribute("role", role);
            request.getRequestDispatcher("emp_p_c_confirm.jsp").forward(request, response);
        }
    }
    
    // ③ 最終確認後、更新ボタンが押された場合のパスワード更新処理
    private void updatePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 「キャンセル」が押された場合はメインページへ戻る
        String confirm = request.getParameter("confirm");
        if ("キャンセル".equals(confirm)) {
            response.sendRedirect("mainPage.jsp");
            return;
        }
        
        String employeeId = request.getParameter("employeeId");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        
        boolean hashFlag = false;
        String newPassword = password;
        System.out.println("updatePassword");
        System.out.println(password);
        System.out.println(newPassword);
        
        // ロールが0以外の場合、パスワードをハッシュ化（SHA-256の例）し、フラグをtrueにする
        if (!"0".equals(role)) {
            newPassword = hashPassword(password);
            hashFlag = true;
        }else {
        	newPassword = password;
        	hashFlag = false;
        }
        
        // DBの更新処理
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "UPDATE employee SET emppassword_hash = ?, flag = ? WHERE empid = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, newPassword);
                    stmt.setBoolean(2, hashFlag);
                    stmt.setString(3, employeeId);
                    
                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        // 更新成功時、完了画面へフォワード
                        request.getRequestDispatcher("emp_passC_Success.jsp").forward(request, response);
                    } else {
                        request.setAttribute("errorMsg", "更新に失敗しました。");
                        request.getRequestDispatcher("mainPage.jsp").forward(request, response);
                    }
                }
            }
        } catch (Exception e) {
            throw new ServletException("パスワード更新中にエラーが発生しました", e);
        }
    }
    
    // パスワードをSHA-256でハッシュ化するヘルパーメソッド
    private String hashPassword(String password) throws ServletException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            // 16進数文字列に変換
            BigInteger number = new BigInteger(1, hash);
            StringBuilder hexString = new StringBuilder(number.toString(16));
            while (hexString.length() < 64) {
                hexString.insert(0, '0');
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new ServletException("パスワードハッシュ化に失敗しました", e);
        }
    }
}
