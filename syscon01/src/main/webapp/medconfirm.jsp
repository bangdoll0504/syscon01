<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>処方薬登録 - 確認</title>
  </head>
  <body>
    <h2>処方薬登録（確認画面）</h2>
    <form action="MedicineRegister" method="post">
      <!-- 登録処理へ進むためのパラメータ -->
      <input type="hidden" name="medicineId" value="${medicineId}">
      <input type="hidden" name="medicineName" value="${medicineName}">
      <input type="hidden" name="unit" value="${unit}">
      <table border="1" cellpadding="5">
        <tr>
          <td>薬剤ID</td>
          <td>${medicineId}</td>
        </tr>
        <tr>
          <td>薬剤名</td>
          <td>${medicineName}</td>
        </tr>
        <tr>
          <td>単位</td>
          <td>${unit}</td>
        </tr>
      </table>
      <br>
      <!-- ボタンのname属性「action」により処理を切り替え -->
      <input type="submit" name="action" value="登録">
      <input type="submit" name="action" value="修正">
      <input type="submit" name="action" value="キャンセル">
    </form>
  </body>
</html>
