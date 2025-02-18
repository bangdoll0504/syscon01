<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>仕入先検索</title>
  </head>
  <body>
    <h2>仕入先検索</h2>
    <form action="SupplTelChangeServlet" method="post">
      <!-- 処理の切り替え用 -->
      <input type="hidden" name="action" value="search">
      仕入先ID：<input type="text" name="shiireid" required>
      <input type="submit" value="検索">
      <input type="button" value="キャンセル" onclick="location.href='mainPage.jsp'">
    </form>
    <c:if test="${not empty errorMsg}">
      <p style="color:red;">${errorMsg}</p>
    </c:if>
  </body>
</html>
