<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>電話番号変更確認</title>
  </head>
  <body>
    <h2>電話番号変更確認</h2>
    <form action="SupplTelChangeServlet" method="post">
      <!-- 更新処理へ進むためのパラメータ -->
      <input type="hidden" name="action" value="update">
      <input type="hidden" name="shiireid" value="${shiireid}">
      <!-- 入力された電話番号（変更後）をhiddenで渡す -->
      <input type="hidden" name="newPhone" value="${newPhone}">
      <table border="1">
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
