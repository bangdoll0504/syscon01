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

import com.model.Patient;

@WebServlet("/PatientList")
public class PatientList extends HttpServlet {
    
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
         
        // 検索種別パラメータ
        String searchType = request.getParameter("searchType");
        // 通常検索用（姓もしくは名）の入力値
        String searchTerm = request.getParameter("searchTerm");
        
        List<Patient> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            
            if ("name".equals(searchType) && searchTerm != null && !searchTerm.trim().isEmpty()) {
                // 患者名または患者姓に部分一致するレコード
                sql = "SELECT * FROM patient WHERE patfname LIKE ? OR patlname LIKE ?";
                pstmt = conn.prepareStatement(sql);
                String likeTerm = "%" + searchTerm + "%";
                pstmt.setString(1, likeTerm);
                pstmt.setString(2, likeTerm);
            } else if ("expired".equals(searchType)) {
                // 保険証有効期限が実行当日以前のレコード
                sql = "SELECT * FROM patient WHERE hokenexp <= CURDATE()";
                pstmt = conn.prepareStatement(sql);
            } else {
                // 検索条件がない場合は全件取得
                sql = "SELECT * FROM patient";
                pstmt = conn.prepareStatement(sql);
            }
            
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Patient p = new Patient();
                p.setPatid(rs.getString("patid"));
                p.setPatfname(rs.getString("patfname"));
                p.setPatlname(rs.getString("patlname"));
                p.setHokenmei(rs.getString("hokenmei"));
                p.setHokenexp(rs.getDate("hokenexp"));
                list.add(p);
            }
        } catch(Exception e) {
            throw new ServletException(e);
        } finally {
            try { if(rs != null) rs.close(); } catch(Exception ignore) {}
            try { if(pstmt != null) pstmt.close(); } catch(Exception ignore) {}
            try { if(conn != null) conn.close(); } catch(Exception ignore) {}
        }
        
        request.setAttribute("patientList", list);
        request.setAttribute("searchTerm", searchTerm);
        request.setAttribute("searchType", searchType);
        RequestDispatcher rd = request.getRequestDispatcher("patientList.jsp");
        rd.forward(request, response);
    }
}
