package com.syscon01;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
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

@WebServlet("/patientDrugList")
public class PatientDrugList extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // DB接続情報（適宜変更してください）
    private static final String DB_URL = "jdbc:mysql://localhost:3306/syskadai";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "password";
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
         processRequest(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
         processRequest(request, response);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
        String patid = request.getParameter("patid");
        if (patid == null || patid.trim().isEmpty()) {
            request.setAttribute("errorMsg", "患者IDが入力されていません。");
            RequestDispatcher rd = request.getRequestDispatcher("patientSearch.jsp");
            rd.forward(request, response);
            return;
        }
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String patfname = null;
        String patlname = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            
            // 患者情報を取得
            String sqlPatient = "SELECT patfname, patlname FROM patient WHERE patid = ?";
            pstmt = conn.prepareStatement(sqlPatient);
            pstmt.setString(1, patid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                patfname = rs.getString("patfname");
                patlname = rs.getString("patlname");
            } else {
                request.setAttribute("errorMsg", "患者が見つかりませんでした。");
                RequestDispatcher rd = request.getRequestDispatcher("patientSearch.jsp");
                rd.forward(request, response);
                return;
            }
            rs.close();
            pstmt.close();
            
            // presc, prepill と medicine を結合して処方情報を取得
            String sqlJoin = "SELECT pr.prescNo, pr.prescdate, pp.medicineid, m.medicinename, pp.dosage " +
                             "FROM presc pr " +
                             "JOIN prepill pp ON pr.prescNo = pp.prescNo " +
                             "JOIN medicine m ON pp.medicineid = m.medicineid " +
                             "WHERE pr.patid = ?";
            pstmt = conn.prepareStatement(sqlJoin);
            pstmt.setString(1, patid);
            rs = pstmt.executeQuery();
            
            List<PrescriptionRecord> recordList = new ArrayList<>();
            while (rs.next()) {
                int prescNo = rs.getInt("prescNo");
                Date prescdate = rs.getDate("prescdate");
                String medicineid = rs.getString("medicineid");
                String medicinename = rs.getString("medicinename");
                int dosage = rs.getInt("dosage");
                PrescriptionRecord record = new PrescriptionRecord(prescNo, prescdate, medicineid, medicinename, dosage);
                recordList.add(record);
            }
            
            request.setAttribute("patid", patid);
            request.setAttribute("patfname", patfname);
            request.setAttribute("patlname", patlname);
            request.setAttribute("recordList", recordList);
            
            RequestDispatcher rd = request.getRequestDispatcher("patientDrugList.jsp");
            rd.forward(request, response);
            
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            if (rs != null) try { rs.close(); } catch(Exception ignore){}
            if (pstmt != null) try { pstmt.close(); } catch(Exception ignore){}
            if (conn != null) try { conn.close(); } catch(Exception ignore){}
        }
    }
    
    // 内部クラス：PrescriptionRecord（処方情報の保持）
    public static class PrescriptionRecord {
        private int prescNo;
        private Date prescdate;
        private String medicineid;
        private String medicinename;
        private int dosage;
        
        public PrescriptionRecord(int prescNo, Date prescdate, String medicineid, String medicinename, int dosage) {
            this.prescNo = prescNo;
            this.prescdate = prescdate;
            this.medicineid = medicineid;
            this.medicinename = medicinename;
            this.dosage = dosage;
        }
        public int getPrescNo() {
            return prescNo;
        }
        public Date getPrescdate() {
            return prescdate;
        }
        public String getMedicineid() {
            return medicineid;
        }
        public String getMedicinename() {
            return medicinename;
        }
        public int getDosage() {
            return dosage;
        }
    }
}
