<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.RequestDispatcher" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // サーブレット側で一覧データを "siiregyoshaList" として設定している前提
    if (request.getAttribute("siiregyoshaList") == null) {
        RequestDispatcher rd = request.getRequestDispatcher("SupplListServlet");
        rd.forward(request, response);
        return;
    }
%>
<html>
  <head>
    <title>仕入先一覧表示</title>
    <style>
      .search-form { display: inline-block; margin-right: 20px; }
    </style>
  </head>
  <body>
    <h2>仕入先一覧表示</h2>
    
    <!-- 住所検索フォーム -->
    <form action="SupplListServlet" method="get" class="search-form">
      住所検索：<input type="text" name="searchAddress" value="${searchAddress}" />
      <input type="hidden" name="searchType" value="address" />
      <input type="submit" value="住所検索" />
    </form>
    
    <!-- 資本金検索フォーム -->
    <form action="SupplListServlet" method="get" class="search-form">
      資本金検索：<input type="text" name="searchShihonkin" value="${searchShihonkin}" />
      <input type="hidden" name="searchType" value="shihonkin" />
      <input type="submit" value="資本金検索" />
    </form>
    
    <!-- メインページへ戻るボタン -->
    <input type="button" value="メインページへ戻る" onclick="location.href='mainPage.jsp'" />
    
    <br/><br/>
    
    <!-- 一覧表示テーブル -->
    <table border="1" cellpadding="5">
      <tr>
        <th>仕入先ID</th>
        <th>仕入先名</th>
        <th>仕入先住所</th>
        <th>仕入先電話番号</th>
        <th>資本金</th>
        <th>発注日数</th>
      </tr>
      <c:forEach var="item" items="${siiregyoshaList}">
        <tr>
          <td>${item.shiireid}</td>
          <td>${item.shiiremei}</td>
          <td>${item.shiireaddress}</td>
          <td>${item.shiiretel}</td>
          <td>${item.shihonkin}</td>
          <td>${item.nouki}</td>
        </tr>
      </c:forEach>
    </table>
  </body>
</html>
