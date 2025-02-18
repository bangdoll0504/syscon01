<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>他病院電話番号変更 - 検索</title>
  </head>
  <body>
    <h2>他病院電話番号変更（検索）</h2>
    <form action="OhospTelChangeServlet" method="post">
      <!-- 検索処理用パラメータ -->
      <input type="hidden" name="action" value="search">
      他病院ID：<input type="text" name="tabyouinid" required>
      <br><br>
      <input type="submit" value="検索">
      <input type="button" value="キャンセル" onclick="location.href='mainPage.jsp'">
    </form>
    <c:if test="${not empty errorMsg}">
      <p style="color:red;">${errorMsg}</p>
    </c:if>
  </body>
</html>
