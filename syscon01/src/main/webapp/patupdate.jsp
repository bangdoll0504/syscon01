<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>保険証情報変更 - 入力</title>
  </head>
  <body>
    <h2>保険証情報変更</h2>
    <p>患者ID: ${patid}</p>
    <p>患者名: ${patfname} ${patlname}</p>
    <p>現在の保険証記号番号: ${hokenmei}</p>
    <p>現在の保険証有効期限: ${hokenexp}</p>
    <form action="PatientUpdateController" method="post">
      <input type="hidden" name="action" value="confirm">
      <input type="hidden" name="patid" value="${patid}">
      <input type="hidden" name="patfname" value="${patfname}">
      <input type="hidden" name="patlname" value="${patlname}">
      <input type="hidden" name="hokenmei" value="${hokenmei}">
      <input type="hidden" name="currentHokenexp" value="${hokenexp}">
      <!-- 保険証記号番号：変更がない場合は空欄に -->
      <p>新しい保険証記号番号（変更がない場合は空欄）: <input type="text" name="newHokenmei"></p>
      <!-- 新しい有効期限は必須。現在の有効期限より後の日付を入力 -->
      <p>新しい保険証有効期限: <input type="date" name="newHokenexp" required></p>
      <input type="submit" value="確認">
      <input type="button" value="キャンセル" onclick="location.href='mainPage.jsp'">
    </form>
  </body>
</html>
