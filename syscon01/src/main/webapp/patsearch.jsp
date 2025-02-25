<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>患者検索</title>
  </head>
  <body>
    <h2>患者検索</h2>
    <form action="PatientUpdateController" method="post">
      患者ID: <input type="text" name="patid" required>
      <input type="hidden" name="action" value="search">
      <input type="submit" value="検索">
      <input type="button" value="キャンセル" onclick="location.href='mainPage.jsp'">
    </form>
    <c:if test="${not empty errorMsg}">
      <p style="color:red;">${errorMsg}</p>
    </c:if>
  </body>
</html>
