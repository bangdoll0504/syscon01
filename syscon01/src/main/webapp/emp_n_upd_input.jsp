<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>従業員情報更新入力</title>
  </head>
  <body>
    <h2>従業員情報更新入力</h2>
    <p>従業員ID：${empid}</p>
    <p>現在の姓：${emplname}</p>
    <p>現在の名：${empfname}</p>
    <form action="EmployeeNameChangeServlet" method="post">
      <!-- 更新確認へ渡すためのパラメータ -->
      <input type="hidden" name="action" value="confirm" />
      <input type="hidden" name="empid" value="${empid}" />
      <!-- 元の値（現在の値）を保持 -->
      <input type="hidden" name="oldEmplname" value="${emplname}" />
      <input type="hidden" name="oldEmpfname" value="${empfname}" />
      
      新しい姓：<input type="text" name="newEmplname" value="${emplname}" /><br><br>
      新しい名：<input type="text" name="newEmpfname" value="${empfname}" /><br><br>
      <input type="submit" value="更新" />
    </form>
    <br>
    <a href="mainPage.jsp">キャンセルしてメインページへ戻る</a>
  </body>
</html>
