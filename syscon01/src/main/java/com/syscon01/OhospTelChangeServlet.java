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

@WebServlet("/OhospTelChangeServlet")
public class OhospTelChangeServlet extends HttpServlet {

    // ※環境に合わせてDB接続情報を設定してください
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

    // 検索処理：tabyouinidでレコードを検索
    private void handleSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        String tabyouinid = request.getParameter("tabyouinid");
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            String sql = "SELECT tabyouinmei, tabyouintel FROM tabyouin WHERE tabyouinid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tabyouinid);
            rs = pstmt.executeQuery();
            if(rs.next()){
                String tabyouinmei = rs.getString("tabyouinmei");
                String tabyouintel = rs.getString("tabyouintel");
                // 検索成功の場合、編集画面へフォワード
                request.setAttribute("tabyouinid", tabyouinid);
                request.setAttribute("tabyouinmei", tabyouinmei);
                request.setAttribute("tabyouintel", tabyouintel);
                RequestDispatcher rd = request.getRequestDispatcher("ohosptelupdate.jsp");
                rd.forward(request, response);
            } else {
                // 該当レコードが見つからなかった場合
                request.setAttribute("errorMsg", "見つかりませんでした");
                RequestDispatcher rd = request.getRequestDispatcher("searchTabyouin.jsp");
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
        String tabyouinid = request.getParameter("tabyouinid");
        String dbPhone = request.getParameter("dbPhone");
        String newPhone = request.getParameter("newPhone");
        request.setAttribute("tabyouinid", tabyouinid);
        request.setAttribute("dbPhone", dbPhone);
        request.setAttribute("newPhone", newPhone);
        RequestDispatcher rd = request.getRequestDispatcher("ohosptelupconfirm.jsp");
        rd.forward(request, response);
    }

    // 更新処理：DB上の電話番号を更新
    private void handleUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        String tabyouinid = request.getParameter("tabyouinid");
        String newPhone = request.getParameter("newPhone");
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            String sql = "UPDATE tabyouin SET tabyouintel = ? WHERE tabyouinid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newPhone);
            pstmt.setString(2, tabyouinid);
            int count = pstmt.executeUpdate();
            if(count > 0) {
                request.setAttribute("message", "更新が完了しました");
            } else {
                request.setAttribute("message", "更新に失敗しました");
            }
            RequestDispatcher rd = request.getRequestDispatcher("ohosptelupcomplete.jsp");
            rd.forward(request, response);
        } finally {
            if(pstmt != null) try { pstmt.close(); } catch(Exception ignore){}
            if(conn != null) try { conn.close(); } catch(Exception ignore){}
        }
    }
}
