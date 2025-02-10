<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>ログインページ</title>
</head>
<body>
    <h2>ログイン</h2>
    <form action="login" method="post">
        <label for="userId">ユーザーID:</label>
        <input type="text" name="userId" required><br><br>
        <label for="password">パスワード:</label>
        <input type="password" name="password" required><br><br>
        <input type="submit" value="ログイン">
    </form>
</body>
</html>
