<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>パスワード変更</title>
    <style>
      body { font-family: Arial, sans-serif; margin: 20px; }
      h2 { color: #333; }
      input { margin: 5px 0; }
    </style>
  </head>
  <body>
    <h2>パスワード変更</h2>
    <form action="PasswordChange" method="post">
      <!-- 確認処理用 -->
      <input type="hidden" name="action" value="confirm">
      <p>
         新しいパスワード: <input type="password" name="newPassword" required>
      </p>
      <p>
         パスワード再入力: <input type="password" name="confirmPassword" required>
      </p>
      <p>
         <input type="submit" value="更新">
         <input type="button" value="キャンセル" onclick="location.href='mainPage.jsp'">
      </p>
      <c:if test="${not empty errorMsg}">
         <p style="color:red;">${errorMsg}</p>
      </c:if>
    </form>
  </body>
</html>
