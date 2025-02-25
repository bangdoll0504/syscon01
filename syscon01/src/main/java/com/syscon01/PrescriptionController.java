package com.syscon01;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PrescriptionController")
public class PrescriptionController extends HttpServlet {

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
             if("確認".equals(action) || "confirm".equals(action)) {
                 // 入力画面からの値をそのまま確認画面へ渡す
                 RequestDispatcher rd = request.getRequestDispatcher("pill_patconfirm.jsp");
                 rd.forward(request, response);
             } else if("処方".equals(action)) {
                 handlePrescription(request, response);
             } else if("投薬編集".equals(action)) {
                 // 投薬編集：確認画面から受け取った値をリクエスト属性に設定し、入力画面にフォワード
                 request.setAttribute("patid", request.getParameter("patid"));
                 request.setAttribute("patfname", request.getParameter("patfname"));
                 request.setAttribute("patlname", request.getParameter("patlname"));
                 
                 // 複数選択の値は getParameterValues() で取得
                 request.setAttribute("selectedMedicine", request.getParameterValues("selectedMedicine"));
                 request.setAttribute("selectedMedicineName", request.getParameterValues("selectedMedicineName"));
                 request.setAttribute("dosagePerAdmin", request.getParameterValues("dosagePerAdmin"));
                 request.setAttribute("adminPerDay", request.getParameterValues("adminPerDay"));
                 request.setAttribute("prescriptionDays", request.getParameterValues("prescriptionDays"));
                 
                 RequestDispatcher rd = request.getRequestDispatcher("pill_patreg.jsp");
                 rd.forward(request, response);
             } else if("キャンセル".equals(action)) {
                 response.sendRedirect("mainPage.jsp");
             } else {
                 response.sendRedirect("mainPage.jsp");
             }
         } catch(Exception e) {
             throw new ServletException(e);
         }
    }
    
    private void handlePrescription(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
       String patid = request.getParameter("patid");
       // 複数行の場合は配列で取得
       String[] selectedMedicine = request.getParameterValues("selectedMedicine");
       String[] dosageArr = request.getParameterValues("dosagePerAdmin");
       String[] adminArr = request.getParameterValues("adminPerDay");
       String[] daysArr = request.getParameterValues("prescriptionDays");
       
       // 例外処理：必ず各配列の長さが一致していることを確認
       if(selectedMedicine == null || dosageArr == null || adminArr == null || daysArr == null ||
          !(selectedMedicine.length == dosageArr.length && dosageArr.length == adminArr.length && adminArr.length == daysArr.length)) {
           throw new ServletException("入力された数量情報が不正です。");
       }
       
       Connection conn = null;
       PreparedStatement pstmtPresc = null;
       PreparedStatement pstmtPrepill = null;
       ResultSet rsKeys = null;
       try {
           Class.forName("com.mysql.cj.jdbc.Driver");
           conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
           conn.setAutoCommit(false);
           
           // presc テーブルへINSERT（処方IDは自動採番、処方日は CURDATE()）
           String sqlPresc = "INSERT INTO presc (patid, prescdate) VALUES (?, CURDATE())";
           pstmtPresc = conn.prepareStatement(sqlPresc, Statement.RETURN_GENERATED_KEYS);
           pstmtPresc.setString(1, patid);
           int count = pstmtPresc.executeUpdate();
           if(count == 0) {
               throw new SQLException("presc テーブルへのINSERTに失敗しました");
           }
           rsKeys = pstmtPresc.getGeneratedKeys();
           int prescNo = 0;
           if(rsKeys.next()) {
               prescNo = rsKeys.getInt(1);
           } else {
               throw new SQLException("処方IDが生成されませんでした");
           }
           
           // 各選択された薬剤について、prepill テーブルに INSERT
           String sqlPrepill = "INSERT INTO prepill (prescNo, medicineid, dosage) VALUES (?, ?, ?)";
           pstmtPrepill = conn.prepareStatement(sqlPrepill);
           
           // 各行ごとに数量を個別に計算する
           for (int i = 0; i < selectedMedicine.length; i++) {
               int dosagePerAdmin = Integer.parseInt(dosageArr[i]);
               int adminPerDay = Integer.parseInt(adminArr[i]);
               int prescriptionDays = Integer.parseInt(daysArr[i]);
               int totalDosage = dosagePerAdmin * adminPerDay * prescriptionDays;
               
               pstmtPrepill.setInt(1, prescNo);
               pstmtPrepill.setString(2, selectedMedicine[i]);
               pstmtPrepill.setInt(3, totalDosage);
               pstmtPrepill.addBatch();
           }
           pstmtPrepill.executeBatch();
           conn.commit();
           
           request.setAttribute("message", "処方が完了しました");
       } catch(Exception ex) {
           if(conn != null) {
               try { conn.rollback(); } catch(Exception ignore) {}
           }
           throw ex;
       } finally {
           if(rsKeys != null) try { rsKeys.close(); } catch(Exception ignore) {}
           if(pstmtPrepill != null) try { pstmtPrepill.close(); } catch(Exception ignore) {}
           if(pstmtPresc != null) try { pstmtPresc.close(); } catch(Exception ignore) {}
           if(conn != null) try { conn.close(); } catch(Exception ignore) {}
       }
       RequestDispatcher rd = request.getRequestDispatcher("pill_patcomplete.jsp");
       rd.forward(request, response);
   }
}
