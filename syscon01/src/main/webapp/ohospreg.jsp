<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>他病院情報入力</title>
  </head>
  <body>
    <h2>他病院情報入力</h2>
    <form action="OhospRegisterServlet" method="post">
      <!-- 処理の切り替え用パラメータ -->
      <input type="hidden" name="action" value="confirm">
      他病院ID：<input type="text" name="tabyouinid" required><br><br>
      他病院名：<input type="text" name="tabyouinmei"><br><br>
      他病院住所：<input type="text" name="tabyouinaddress"><br><br>
      他病院電話番号：<input type="text" name="tabyouintel"><br><br>
      資本金：<input type="number" name="tabyouinshihonkin"><br><br>
      救急：
      <!-- 0：非救急、1：救急 -->
      <input type="radio" name="kyukyu" value="1" required>救急
      <input type="radio" name="kyukyu" value="0" required checked>非救急
      <br><br>
      <input type="submit" value="確認">
      <input type="button" value="キャンセル" onclick="location.href='mainPage.jsp'">
    </form>
  </body>
</html>
