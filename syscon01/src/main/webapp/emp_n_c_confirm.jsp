<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>更新内容確認</title>
  </head>
  <body>
    <h2>更新内容の最終確認</h2>
    <form action="EmployeeNameChangeServlet" method="post">
      <input type="hidden" name="action" value="update" />
      <input type="hidden" name="empid" value="${empid}" />
      <input type="hidden" name="newEmpfname" value="${newEmpfname}" />
      <input type="hidden" name="newEmplname" value="${newEmplname}" />
      <table border="1">
        <tr>
          <th></th>
          <th>現在の値</th>
          <th>変更後の値</th>
        </tr>
        <tr>
          <td>姓</td>
          <td>${oldEmplname}</td>
          <td>${newEmplname}</td>
        </tr>
        <tr>
          <td>名</td>
          <td>${oldEmpfname}</td>
          <td>${newEmpfname}</td>
        </tr>
      </table>
      <br>
      <input type="submit" value="変更" />
      <input type="button" value="キャンセル" onclick="location.href='mainPage.jsp'" />
    </form>
  </body>
</html>
