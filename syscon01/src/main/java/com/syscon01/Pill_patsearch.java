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

import com.model.Medicine;

@WebServlet("/Pill_patsearch")
public class Pill_patsearch extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/syskadai";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "password";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
        String patid = request.getParameter("patid");
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            String sql = "SELECT patid, patfname, patlname FROM patient WHERE patid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, patid);
            rs = pstmt.executeQuery();
            if(rs.next()){
                request.setAttribute("patid", rs.getString("patid"));
                request.setAttribute("patfname", rs.getString("patfname"));
                request.setAttribute("patlname", rs.getString("patlname"));
                
                // medicineテーブルから処方薬一覧を取得
                List<Medicine> medicineList = new ArrayList<>();
                PreparedStatement pstmtMed = null;
                ResultSet rsMed = null;
                try {
                    String sqlMed = "SELECT medicineid, medicinename, unit FROM medicine";
                    pstmtMed = conn.prepareStatement(sqlMed);
                    rsMed = pstmtMed.executeQuery();
                    while(rsMed.next()){
                        Medicine med = new Medicine();
                        med.setMedicineid(rsMed.getString("medicineid"));
                        med.setMedicinename(rsMed.getString("medicinename"));
                        med.setUnit(rsMed.getString("unit"));
                        medicineList.add(med);
                    }
                } finally {
                    if(rsMed != null) try { rsMed.close(); } catch(Exception ignore){}
                    if(pstmtMed != null) try { pstmtMed.close(); } catch(Exception ignore){}
                }
                request.setAttribute("medicineList", medicineList);
                
                RequestDispatcher rd = request.getRequestDispatcher("pill_patreg.jsp");
                rd.forward(request, response);
            } else {
                request.setAttribute("errorMsg", "患者が見つかりませんでした");
                RequestDispatcher rd = request.getRequestDispatcher("searchPatient.jsp");
                rd.forward(request, response);
            }
        } catch(Exception e) {
            throw new ServletException(e);
        } finally {
            if(rs != null) try { rs.close(); } catch(Exception ignore){}
            if(pstmt != null) try { pstmt.close(); } catch(Exception ignore){}
            if(conn != null) try { conn.close(); } catch(Exception ignore){}
        }
    }
}
