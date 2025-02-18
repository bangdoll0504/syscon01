package com.syscon01;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.model.Tabyouin;

@WebServlet("/OhospListServlet")
public class OhospListServlet extends HttpServlet {
    
    // ※実際の環境に合わせてDB接続情報を設定してください
    private static final String DB_URL = "jdbc:mysql://localhost:3306/syskadai";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "password";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
  
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 検索区分： "address" または "shihonkin" を受け取る
        String searchType = request.getParameter("searchType");
        String searchAddress = request.getParameter("searchAddress");
        String searchShihonkin = request.getParameter("searchShihonkin");
        
        List<Tabyouin> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM tabyouin"; // 初回実行時は全件表示
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            if ("address".equals(searchType) && searchAddress != null && !searchAddress.trim().isEmpty()) {
                sql = "SELECT * FROM tabyouin WHERE tabyouinaddress LIKE ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, "%" + searchAddress + "%");
            } else if ("shihonkin".equals(searchType) && searchShihonkin != null && !searchShihonkin.trim().isEmpty()) {
                sql = "SELECT * FROM tabyouin WHERE tabyouinshihonkin >= ?";
                pstmt = conn.prepareStatement(sql);
                int shihonkinVal = Integer.parseInt(searchShihonkin);
                pstmt.setInt(1, shihonkinVal);
            } else {
                pstmt = conn.prepareStatement(sql);
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Tabyouin t = new Tabyouin();
                t.setTabyouinid(rs.getString("tabyouinid"));
                t.setTabyouinmei(rs.getString("tabyouinmei"));
                t.setTabyouinaddress(rs.getString("tabyouinaddress"));
                t.setTabyouintel(rs.getString("tabyouintel"));
                t.setTabyouinshihonkin(rs.getInt("tabyouinshihonkin"));
                t.setKyukyu(rs.getInt("kyukyu"));
                list.add(t);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignore) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception ignore) {}
            try { if (conn != null) conn.close(); } catch (Exception ignore) {}
        }
        
        request.setAttribute("tabyouinList", list);
        request.setAttribute("searchAddress", searchAddress);
        request.setAttribute("searchShihonkin", searchShihonkin);
        RequestDispatcher rd = request.getRequestDispatcher("ohosplist.jsp");
        rd.forward(request, response);
    }
}
