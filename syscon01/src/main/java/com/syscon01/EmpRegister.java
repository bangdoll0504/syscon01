package com.syscon01;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/empregister")
public class EmpRegister extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String empId = request.getParameter("empId");
        String emplname = request.getParameter("emplname");
        String empfname = request.getParameter("empfname");
        String emppassword = request.getParameter("emppassword");
        int emprole = Integer.parseInt(request.getParameter("emprole"));

        String hashedPassword = emppassword;
        boolean flag = false;

        if (emprole != 0) {
            hashedPassword = hashPassword(emppassword);
            flag = true;
        }

        // データベース接続情報
        String url = "jdbc:mysql://localhost:3306/syskadai";
        String username = "root";
        String password = "password";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);

            String sql = "INSERT INTO employee (empId, emplname, empfname, emppassword_hash, emprole, flag) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, empId);
            stmt.setString(2, emplname);
            stmt.setString(3, empfname);
            stmt.setString(4, hashedPassword);
            stmt.setInt(5, emprole);
            stmt.setBoolean(6, flag);

            int rowsInserted = stmt.executeUpdate();
            stmt.close();
            conn.close();

            if (rowsInserted > 0) {
                response.sendRedirect("empcomplete.jsp");
            } else {
                response.sendRedirect("errorPage.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("errorPage.jsp");
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
