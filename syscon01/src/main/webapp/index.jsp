<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>ログイン - Modern Web Design</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <!-- 外部CSSを読み込み -->
  <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
  <div class="login-container">
    <form action="<c:url value='/login' />" method="post">
      <h2>ログイン</h2>
      <div class="input-group">
        <label for="userId">ユーザーID</label>
        <input type="text" id="userId" name="userId" placeholder="ユーザーIDを入力" required>
      </div>
      <div class="input-group">
        <label for="password">パスワード</label>
        <input type="password" id="password" name="password" placeholder="パスワードを入力" required>
      </div>
      <button type="submit" class="btn">ログイン</button>
    </form>
  </div>
</body>
</html>
