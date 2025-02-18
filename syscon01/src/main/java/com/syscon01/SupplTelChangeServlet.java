package com.syscon01;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SupplTelChangeServlet")
public class SupplTelChangeServlet extends HttpServlet {

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
            switch(action) {
                case "search":
                    handleSearch(request, response);
                    break;
                case "confirm":
                    handleConfirm(request, response);
                    break;
                case "update":
                    handleUpdate(request, response);
                    break;
                default:
                    response.sendRedirect("mainPage.jsp");
                    break;
            }
        } catch(Exception e) {
            throw new ServletException(e);
        }
    }

    // 検索処理：仕入先IDで検索し、該当レコードがあれば編集画面へ
    private void handleSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        String shiireid = request.getParameter("shiireid");
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            String sql = "SELECT shiiremei, shiiretel FROM shiiregyosha WHERE shiireid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shiireid);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                String shiiremei = rs.getString("shiiremei");
                String shiiretel = rs.getString("shiiretel");
                request.setAttribute("shiireid", shiireid);
                request.setAttribute("shiiremei", shiiremei);
                request.setAttribute("shiiretel", shiiretel);
                RequestDispatcher rd = request.getRequestDispatcher("supplTelUpdate.jsp");
                rd.forward(request, response);
            } else {
                request.setAttribute("errorMsg", "見つかりませんでした");
                RequestDispatcher rd = request.getRequestDispatcher("supplTelSearch.jsp");
                rd.forward(request, response);
            }
        } finally {
            if(rs != null) try { rs.close(); } catch(Exception ignore){}
            if(pstmt != null) try { pstmt.close(); } catch(Exception ignore){}
            if(conn != null) try { conn.close(); } catch(Exception ignore){}
        }
    }

    // 確認処理：入力された新しい電話番号とDBの電話番号を確認画面へ渡す
    private void handleConfirm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String shiireid = request.getParameter("shiireid");
        String dbPhone = request.getParameter("dbPhone");
        String newPhone = request.getParameter("newPhone");
        request.setAttribute("shiireid", shiireid);
        request.setAttribute("dbPhone", dbPhone);
        request.setAttribute("newPhone", newPhone);
        RequestDispatcher rd = request.getRequestDispatcher("suppltelconfirm.jsp");
        rd.forward(request, response);
    }

    // 更新処理：電話番号を更新し完了画面へ
    private void handleUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        String shiireid = request.getParameter("shiireid");
        String newPhone = request.getParameter("newPhone");
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            String sql = "UPDATE shiiregyosha SET shiiretel = ? WHERE shiireid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newPhone);
            pstmt.setString(2, shiireid);
            int count = pstmt.executeUpdate();
            if(count > 0) {
                request.setAttribute("message", "更新が完了しました");
            } else {
                request.setAttribute("message", "更新に失敗しました");
            }
            RequestDispatcher rd = request.getRequestDispatcher("suppltelupcomplete.jsp");
            rd.forward(request, response);
        } finally {
            if(pstmt != null) try { pstmt.close(); } catch(Exception ignore){}
            if(conn != null) try { conn.close(); } catch(Exception ignore){}
        }
    }
}
