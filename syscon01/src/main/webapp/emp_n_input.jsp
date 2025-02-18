<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>従業員検索</title>
  </head>
  <body>
    <h2>従業員検索</h2>
    <form action="EmployeeNameChangeServlet" method="post">
      <!-- 処理の切り替え用パラメータ -->
      <input type="hidden" name="action" value="search" />
      従業員ID：<input type="text" name="empid" required />
      <input type="submit" value="検索" />
    </form>
    <!-- エラーメッセージがある場合の表示 -->
    <c:if test="${not empty errorMsg}">
      <p style="color:red;">${errorMsg}</p>
    </c:if>
    <br>
    <a href="mainPage.jsp">メインページへ戻る</a>
  </body>
</html>
