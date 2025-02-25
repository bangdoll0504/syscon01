<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.RequestDispatcher" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // 直接このJSPにアクセスされた場合、一覧データがない場合はコントローラへフォワード
    if (request.getAttribute("patientList") == null) {
        RequestDispatcher rd = request.getRequestDispatcher("PatientList");
        rd.forward(request, response);
        return;
    }
%>
<html>
  <head>
    <title>患者一覧表示</title>
    <style>
      .search-form { margin-bottom: 20px; }
      table { border-collapse: collapse; width: 100%; }
      th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
    </style>
  </head>
  <body>
    <h2>患者一覧表示</h2>
    
    <!-- 検索フォーム -->
    <form action="PatientList" method="get" class="search-form">
      姓もしくは名検索：<input type="text" name="searchTerm" value="${searchTerm}" />
      <input type="submit" value="検索" />
      <input type="button" value="メインページへ戻る" onclick="location.href='mainPage.jsp'" />
    </form>
    
    <!-- 一覧表示テーブル -->
    <table>
      <tr>
        <th>患者ID</th>
        <th>患者姓</th>
        <th>患者名</th>
        <th>保険証記号番号</th>
        <th>保険証有効期限</th>
      </tr>
      <c:forEach var="patient" items="${patientList}">
        <tr>
          <td>${patient.patid}</td>
          <td>${patient.patlname}</td>
          <td>${patient.patfname}</td>
          <td>${patient.hokenmei}</td>
          <td>${patient.hokenexp}</td>
        </tr>
      </c:forEach>
    </table>
  </body>
</html>
