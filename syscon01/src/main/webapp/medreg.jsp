<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>処方薬登録 - 入力</title>
  </head>
  <body>
    <h2>処方薬登録（入力画面）</h2>
    <form action="MedicineRegister" method="post">
      <!-- 処理切替用パラメータ -->
      <input type="hidden" name="action" value="confirm">
      薬剤ID：<input type="text" name="medicineId" required><br><br>
      薬剤名：<input type="text" name="medicineName" required><br><br>
      単位：
      <select name="unit">
        <option value="枚">枚</option>
        <option value="ml">ml</option>
        <option value="本">本</option>
      </select>
      <br><br>
      <input type="submit" value="確認">
      <input type="button" value="キャンセル" onclick="location.href='mainPage.jsp'">
    </form>
  </body>
</html>
