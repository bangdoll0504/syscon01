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

@WebServlet("/EmployeeNameChangeServlet")
public class Emp_NameChangeServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/syskadai";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "password";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // リクエストパラメータ "action" により処理を切り替え
        String action = request.getParameter("action");
        if (action == null) {
            action = "search";  // デフォルトは検索処理
        }

        try {
            switch (action) {
                case "search":
                    handleSearch(request, response);		// 従業員IDで検索
                    break;
                case "confirm":
                    handleConfirm(request, response);		// 姓又は名の変更確認
                    break;
                case "update":
                    handleUpdate(request, response);		// 姓又は名の変更を行う
                    break;
                default:
                    response.sendRedirect("mainPage.jsp");	// 3つの機能に該当しないときはメインページを表示
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // 従業員検索処理
    private void handleSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        String empid = request.getParameter("empid");
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            String sql = "SELECT empfname, emplname FROM employee WHERE empid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, empid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String empfname = rs.getString("empfname");
                String emplname = rs.getString("emplname");
                request.setAttribute("empid", empid);
                request.setAttribute("empfname", empfname);
                request.setAttribute("emplname", emplname);
                RequestDispatcher rd = request.getRequestDispatcher("emp_n_upd_input.jsp");
                rd.forward(request, response);
            } else {
                request.setAttribute("errorMsg", "従業員IDが見つかりません。");
                RequestDispatcher rd = request.getRequestDispatcher("searchEmployee.jsp");
                rd.forward(request, response);
            }
        } finally {
            if (rs != null) try { rs.close(); } catch (Exception ignore) {}
            if (pstmt != null) try { pstmt.close(); } catch (Exception ignore) {}
            if (conn != null) try { conn.close(); } catch (Exception ignore) {}
        }
    }

 // 更新確認処理
    private void handleConfirm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String empid = request.getParameter("empid");
        String newEmpfname = request.getParameter("newEmpfname");
        String newEmplname = request.getParameter("newEmplname");
        
        // updateEmployeeForm.jsp で送信された元の値を取得
        String oldEmpfname = request.getParameter("oldEmpfname");
        String oldEmplname = request.getParameter("oldEmplname");

        request.setAttribute("empid", empid);
        request.setAttribute("newEmpfname", newEmpfname);
        request.setAttribute("newEmplname", newEmplname);
        // 元の値を正しくセット
        request.setAttribute("oldEmpfname", oldEmpfname);
        request.setAttribute("oldEmplname", oldEmplname);
        RequestDispatcher rd = request.getRequestDispatcher("emp_n_c_confirm.jsp");
        rd.forward(request, response);
    }

    // データ更新処理
    private void handleUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        String empid = request.getParameter("empid");
        String newEmpfname = request.getParameter("newEmpfname");
        String newEmplname = request.getParameter("newEmplname");
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            String sql = "UPDATE employee SET empfname = ?, emplname = ? WHERE empid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newEmpfname);
            pstmt.setString(2, newEmplname);
            pstmt.setString(3, empid);
            int updateCount = pstmt.executeUpdate();
            if (updateCount > 0) {
                request.setAttribute("message", "完了しました");
                RequestDispatcher rd = request.getRequestDispatcher("emp_nC_Sccess.jsp");
                rd.forward(request, response);
            } else {
                request.setAttribute("errorMsg", "更新に失敗しました");
                RequestDispatcher rd = request.getRequestDispatcher("searchEmployee.jsp");
                rd.forward(request, response);
            }
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch (Exception ignore) {}
            if (conn != null) try { conn.close(); } catch (Exception ignore) {}
        }
    }
}
