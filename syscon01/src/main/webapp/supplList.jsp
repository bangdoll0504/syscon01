<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // リクエスト属性 "siiregyoshaList" が設定されていなければ、コントローラへフォワード
    if (request.getAttribute("siiregyoshaList") == null) {
        RequestDispatcher rd = request.getRequestDispatcher("SupplListServlet");
        rd.forward(request, response);
        return;
    }
%>
<html>
  <head>
    <title>仕入業者一覧表示</title>
  </head>
  <body>
    <h2>仕入業者一覧表示</h2>
    <!-- 画面上部に検索フォーム -->
    <form action="SupplListServlet" method="get">
      住所検索：<input type="text" name="searchAddress" value="${searchAddress}" />
      <input type="submit" value="検索" />
      <input type="button" value="メインページへ戻る" onclick="location.href='mainPage.jsp'" />
    </form>
    <br>
    <!-- テーブルで一覧表示 -->
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
