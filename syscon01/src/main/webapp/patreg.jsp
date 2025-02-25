<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>患者情報入力</title>
  </head>
  <body>
    <h2>患者情報入力</h2>
    <form action="PatiRegister" method="post">
      <!-- 次画面への処理切り替え用パラメータ -->
      <input type="hidden" name="action" value="confirm" />
      患者ID：<input type="text" name="patid" required /><br><br>
      患者姓：<input type="text" name="patlname" /><br><br>
      患者名：<input type="text" name="patfname" /><br><br>
      保険証記号番号：<input type="text" name="hokenmei" /><br><br>
      保険証有効期限：<input type="date" name="hokenexp" /><br><br>
      <input type="submit" value="確認" />
      <input type="button" value="キャンセル" onclick="location.href='mainPage.jsp'" />
    </form>
  </body>
</html>
