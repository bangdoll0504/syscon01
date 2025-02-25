package com.syscon01;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
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

@WebServlet("/PatientUpdateController")
public class PatientUpdateController extends HttpServlet {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/syskadai";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "password";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action == null) {
			response.sendRedirect("mainPage.jsp");
			return;
		}
		try {
			if ("search".equals(action)) {
				handleSearch(request, response);
			} else if ("confirm".equals(action)) {
				handleConfirm(request, response);
			} else if ("変更".equals(action)) {
				handleUpdate(request, response);
			} else if ("編集".equals(action)) {
				// 編集の場合は、入力画面に戻る（入力内容をリクエスト属性としてセット）
				RequestDispatcher rd = request.getRequestDispatcher("patupdate.jsp");
				rd.forward(request, response);
			} else if ("キャンセル".equals(action)) {
				response.sendRedirect("mainPage.jsp");
			} else {
				response.sendRedirect("mainPage.jsp");
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	// 患者検索
	private void handleSearch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ClassNotFoundException, SQLException {
		String patid = request.getParameter("patid");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			String sql = "SELECT patid, patfname, patlname, hokenmei, hokenexp FROM patient WHERE patid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, patid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				request.setAttribute("patid", rs.getString("patid"));
				request.setAttribute("patfname", rs.getString("patfname"));
				request.setAttribute("patlname", rs.getString("patlname"));
				request.setAttribute("hokenmei", rs.getString("hokenmei"));
				request.setAttribute("hokenexp", rs.getDate("hokenexp").toString());
				RequestDispatcher rd = request.getRequestDispatcher("patupdate.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("errorMsg", "患者が見つかりませんでした");
				RequestDispatcher rd = request.getRequestDispatcher("patsearch.jsp");
				rd.forward(request, response);
			}
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception ignore) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception ignore) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception ignore) {
				}
		}
	}

	// 確認処理：入力された新しい保険証情報をチェックして確認画面へ
	private void handleConfirm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String newHokenexp = request.getParameter("newHokenexp");
		String currentHokenexp = request.getParameter("currentHokenexp");

		if (newHokenexp == null || newHokenexp.trim().isEmpty() ||
				currentHokenexp == null || currentHokenexp.trim().isEmpty()) {
			request.setAttribute("errorMsg", "新しい有効期限および現在の有効期限は必須です。");
			RequestDispatcher rd = request.getRequestDispatcher("patupdate.jsp");
			rd.forward(request, response);
			return;
		}

		try {
			Date currDate = Date.valueOf(currentHokenexp);
			Date newDate = Date.valueOf(newHokenexp);
			if (!newDate.after(currDate)) {
				request.setAttribute("errorMsg", "新しい保険証有効期限は現在の有効期限より後の日付でなければなりません。");
				RequestDispatcher rd = request.getRequestDispatcher("patupdate.jsp");
				rd.forward(request, response);
				return;
			}
		} catch (IllegalArgumentException ex) {
			request.setAttribute("errorMsg", "日付の形式が正しくありません。");
			RequestDispatcher rd = request.getRequestDispatcher("patupconfirm.jsp");
			rd.forward(request, response);
			return;
		}

		// 患者情報などもセット
		request.setAttribute("patid", request.getParameter("patid"));
		request.setAttribute("patfname", request.getParameter("patfname"));
		request.setAttribute("patlname", request.getParameter("patlname"));
		request.setAttribute("hokenmei", request.getParameter("hokenmei")); // 現在の保険証記号番号
		request.setAttribute("currentHokenexp", currentHokenexp);
		request.setAttribute("newHokenmei", request.getParameter("newHokenmei"));
		request.setAttribute("newHokenexp", newHokenexp);

		RequestDispatcher rd = request.getRequestDispatcher("patupconfirm.jsp");
		rd.forward(request, response);
	}

	// 更新処理：保険証情報の更新
	private void handleUpdate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ClassNotFoundException, SQLException {
		String patid = request.getParameter("patid");
		String newHokenmei = request.getParameter("newHokenmei");
		String newHokenexp = request.getParameter("newHokenexp");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			String sql;
			if (newHokenmei == null || newHokenmei.trim().isEmpty()) {
				// 保険証記号番号に変更がない場合は、有効期限のみ更新
				sql = "UPDATE patient SET hokenexp = ? WHERE patid = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setDate(1, Date.valueOf(newHokenexp));
				pstmt.setString(2, patid);
			} else {
				// 保険証記号番号が変更されている場合は、両方更新
				sql = "UPDATE patient SET hokenmei = ?, hokenexp = ? WHERE patid = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, newHokenmei);
				pstmt.setDate(2, Date.valueOf(newHokenexp));
				pstmt.setString(3, patid);
			}
			int count = pstmt.executeUpdate();
			if (count > 0) {
				request.setAttribute("message", "保険証情報の変更が完了しました。");
			} else {
				request.setAttribute("message", "変更に失敗しました。");
			}
			RequestDispatcher rd = request.getRequestDispatcher("patupcomplete.jsp");
			rd.forward(request, response);
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception ignore) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception ignore) {
				}
		}
	}
}
