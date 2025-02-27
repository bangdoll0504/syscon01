<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>患者検索</title>
    <style>
      body { font-family: Arial, sans-serif; margin: 20px; }
      label { font-weight: bold; }
      .error { color: red; }
    </style>
  </head>
  <body>
    <h2>患者検索</h2>
    <form action="patientDrugList" method="get">
      <label for="patid">患者ID:</label>
      <input type="text" id="patid" name="patid" required>
      <input type="submit" value="検索">
      <input type="button" value="キャンセル" onclick="location.href='mainPage.jsp'">
    </form>
    <c:if test="${not empty errorMsg}">
      <p class="error">${errorMsg}</p>
    </c:if>
  </body>
</html>
