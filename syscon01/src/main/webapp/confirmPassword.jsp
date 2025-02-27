<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>パスワード変更確認</title>
    <style>
      body { font-family: Arial, sans-serif; margin: 20px; }
      h2 { color: #333; }
      table { border-collapse: collapse; }
      td { padding: 5px; }
    </style>
  </head>
  <body>
    <h2>パスワード変更確認</h2>
    <table>
      <tr>
        <td>新しいパスワード:</td>
        <td>${param.newPassword}</td>
      </tr>
      <tr>
        <td>パスワード再入力:</td>
        <td>${param.confirmPassword}</td>
      </tr>
    </table>
    <br>
    <form action="PasswordChange" method="post">
      <!-- 入力されたパスワードをhiddenで再送 -->
      <input type="hidden" name="newPassword" value="${param.newPassword}">
      <input type="hidden" name="confirmPassword" value="${param.confirmPassword}">
      <input type="hidden" name="action" value="change">
      <input type="submit" value="変更">
      <input type="submit" name="action" value="編集">
      <input type="submit" name="action" value="キャンセル">
    </form>
  </body>
</html>
