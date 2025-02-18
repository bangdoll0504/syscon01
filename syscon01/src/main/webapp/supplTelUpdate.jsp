<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>電話番号編集</title>
  </head>
  <body>
    <h2>電話番号編集</h2>
    <p>仕入先ID：${shiireid}</p>
    <p>仕入先名：${shiiremei}</p>
    <form action="SupplTelChangeServlet" method="post">
      <!-- 次画面へ渡すためのパラメータ -->
      <input type="hidden" name="action" value="confirm">
      <input type="hidden" name="shiireid" value="${shiireid}">
      <!-- DBから取得した元の電話番号をhiddenで保持 -->
      <input type="hidden" name="dbPhone" value="${shiiretel}">
      電話番号：<input type="text" name="newPhone" value="${shiiretel}" required>
      <br><br>
      <input type="submit" value="確認">
      <input type="button" value="キャンセル" onclick="location.href='mainPage.jsp'">
    </form>
  </body>
</html>
