<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.RequestDispatcher" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if (request.getAttribute("tabyouinList") == null) {
        RequestDispatcher rd = request.getRequestDispatcher("OhospListServlet");
        rd.forward(request, response);
        return;
    }
%>
<html>
  <head>
    <title>他病院一覧表示</title>
    <style>
      .search-form { display: inline-block; margin-right: 20px; }
      table { border-collapse: collapse; }
      th, td { padding: 8px; border: 1px solid #ccc; }
    </style>
  </head>
  <body>
    <h2>他病院一覧表示</h2>
    
    <!-- 住所検索フォーム -->
    <form action="OhospListServlet" method="get" class="search-form">
      住所検索：<input type="text" name="searchAddress" value="${searchAddress}" />
      <input type="hidden" name="searchType" value="address" />
      <input type="submit" value="住所検索" />
    </form>
    
    <!-- 資本金検索フォーム -->
    <form action="OhospListServlet" method="get" class="search-form">
      資本金検索：<input type="text" name="searchShihonkin" value="${searchShihonkin}" />
      <input type="hidden" name="searchType" value="shihonkin" />
      <input type="submit" value="資本金検索" />
    </form>
    
    <!-- メインページへ戻るボタン -->
    <input type="button" value="メインページへ戻る" onclick="location.href='mainPage.jsp'" />
    
    <br/><br/>
    
    <!-- 一覧表示テーブル -->
    <table>
      <tr>
        <th>他病院ID</th>
        <th>他病院名</th>
        <th>他病院住所</th>
        <th>他病院電話番号</th>
        <th>資本金</th>
        <th>救急</th>
      </tr>
      <c:forEach var="item" items="${tabyouinList}">
        <tr>
          <td>${item.tabyouinid}</td>
          <td>${item.tabyouinmei}</td>
          <td>${item.tabyouinaddress}</td>
          <td>${item.tabyouintel}</td>
          <td>${item.tabyouinshihonkin}</td>
          <td>
            <c:choose>
              <c:when test="${item.kyukyu == 1}">救急</c:when>
              <c:otherwise>非救急</c:otherwise>
            </c:choose>
          </td>
        </tr>
      </c:forEach>
    </table>
  </body>
</html>
