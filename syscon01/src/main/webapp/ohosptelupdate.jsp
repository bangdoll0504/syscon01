<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>他病院電話番号変更 - 編集</title>
  </head>
  <body>
    <h2>他病院電話番号変更（編集）</h2>
    <!-- 画面上部にタブ病院IDと病院名、現在の電話番号を表示 -->
    <p>他病院ID：${tabyouinid}</p>
    <p>他病院名：${tabyouinmei}</p>
    <form action="OhospTelChangeServlet" method="post">
      <!-- 次画面へ渡すためのパラメータ -->
      <input type="hidden" name="action" value="confirm">
      <input type="hidden" name="tabyouinid" value="${tabyouinid}">
      <!-- DBから取得した現在の電話番号をhiddenで保持 -->
      <input type="hidden" name="dbPhone" value="${tabyouintel}">
      
      電話番号：<input type="text" name="newPhone" value="${tabyouintel}" required>
      <br><br>
      <input type="submit" value="確認">
      <input type="button" value="キャンセル" onclick="location.href='mainPage.jsp'">
    </form>
  </body>
</html>
