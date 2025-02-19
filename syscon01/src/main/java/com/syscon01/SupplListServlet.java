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

import com.model.Siiregyosha;

@WebServlet("/SupplListServlet")
public class SupplListServlet extends HttpServlet {
    
    // ※実際の環境に合わせてDB_URL、DB_USER、DB_PASSを設定してください
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
        
        // 住所検索の場合：searchType="address"、入力値はsearchAddress
        // 資本金検索の場合：searchType="shihonkin"、入力値はsearchShihonkin
        String searchType = request.getParameter("searchType");
        String searchAddress = request.getParameter("searchAddress");
        String searchShihonkin = request.getParameter("searchShihonkin");
        
        List<Siiregyosha> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM shiiregyosha"; // 初期は全件表示
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            
            if ("address".equals(searchType) && searchAddress != null && !searchAddress.trim().isEmpty()) {
                sql = "SELECT * FROM shiiregyosha WHERE shiireaddress LIKE ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, "%" + searchAddress + "%");
            } else if ("shihonkin".equals(searchType) && searchShihonkin != null && !searchShihonkin.trim().isEmpty()) {
                sql = "SELECT * FROM shiiregyosha WHERE shihonkin >= ?";
                pstmt = conn.prepareStatement(sql);
                int shihonkinVal = Integer.parseInt(searchShihonkin);
                pstmt.setInt(1, shihonkinVal);
            } else {
                // 検索条件がない場合は全件取得
                pstmt = conn.prepareStatement(sql);
            }
            
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Siiregyosha s = new Siiregyosha();
                s.setShiireid(rs.getString("shiireid"));
                s.setShiiremei(rs.getString("shiiremei"));
                s.setShiireaddress(rs.getString("shiireaddress"));
                s.setShiiretel(rs.getString("shiiretel"));
                s.setShihonkin(rs.getInt("shihonkin"));
                s.setNouki(rs.getInt("nouki"));
                list.add(s);
            }
            
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignore) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception ignore) {}
            try { if (conn != null) conn.close(); } catch (Exception ignore) {}
        }
        
        // JSPに渡すための属性に検索条件と結果リストをセット
        request.setAttribute("siiregyoshaList", list);
        request.setAttribute("searchAddress", searchAddress);
        request.setAttribute("searchShihonkin", searchShihonkin);
        RequestDispatcher rd = request.getRequestDispatcher("supplList.jsp");
        rd.forward(request, response);
    }
}
