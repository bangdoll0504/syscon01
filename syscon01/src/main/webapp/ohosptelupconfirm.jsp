<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>他病院電話番号変更 - 確認</title>
  </head>
  <body>
    <h2>他病院電話番号変更（確認）</h2>
    <form action="OhospTelChangeServlet" method="post">
      <!-- 更新処理へ進むためのパラメータ -->
      <input type="hidden" name="action" value="update">
      <input type="hidden" name="tabyouinid" value="${tabyouinid}">
      <!-- 入力された新しい電話番号をhiddenで渡す -->
      <input type="hidden" name="newPhone" value="${newPhone}">
      <table border="1" cellpadding="5">
        <tr>
          <td>現在の電話番号</td>
          <td>${dbPhone}</td>
        </tr>
        <tr>
          <td>新しい電話番号</td>
          <td>${newPhone}</td>
        </tr>
      </table>
      <br>
      <input type="submit" value="変更">
      <input type="button" value="キャンセル" onclick="location.href='mainPage.jsp'">
    </form>
  </body>
</html>
