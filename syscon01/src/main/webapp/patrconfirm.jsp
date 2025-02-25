<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>患者情報確認</title>
  </head>
  <body>
    <h2>患者情報確認</h2>
    <form action="PatiRegister" method="post">
      <!-- 登録処理へ進むためのパラメータ -->
      <input type="hidden" name="action" value="register" />
      <!-- 入力内容をhiddenで保持 -->
      <input type="hidden" name="patid" value="${patid}" />
      <input type="hidden" name="patfname" value="${patfname}" />
      <input type="hidden" name="patlname" value="${patlname}" />
      <input type="hidden" name="hokenmei" value="${hokenmei}" />
      <input type="hidden" name="hokenexp" value="${hokenexp}" />
      
      <table border="1" cellpadding="5">
        <tr>
          <td>患者ID</td>
          <td>${patid}</td>
        </tr>
        <tr>
          <td>患者姓</td>
          <td>${patlname}</td>
        </tr>
        <tr>
          <td>患者名</td>
          <td>${patfname}</td>
        </tr>
        <tr>
          <td>保険証記号番号</td>
          <td>${hokenmei}</td>
        </tr>
        <tr>
          <td>保険証有効期限</td>
          <td>${hokenexp}</td>
        </tr>
      </table>
      <br>
      <input type="submit" value="登録" />
      <input type="button" value="キャンセル" onclick="location.href='mainPage.jsp'" />
    </form>
  </body>
</html>
