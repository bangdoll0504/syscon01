<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>保険証情報変更 - 確認</title>
  </head>
  <body>
    <h2>変更確認</h2>
    <p>患者ID: ${param.patid}</p>
    <p>患者名: ${param.patfname} ${param.patlname}</p>
    <p>現在の保険証記号番号: ${param.hokenmei}</p>
    <p>現在の保険証有効期限: ${param.currentHokenexp}</p>
    <c:choose>
      <c:when test="${empty param.newHokenmei}">
         <p>保険証記号番号: 変更なし</p>
      </c:when>
      <c:otherwise>
         <p>新しい保険証記号番号: ${param.newHokenmei}</p>
      </c:otherwise>
    </c:choose>
    <p>新しい保険証有効期限: ${param.newHokenexp}</p>
    
    <form action="PatientUpdateController" method="post">
      <input type="hidden" name="patid" value="${param.patid}">
      <input type="hidden" name="patfname" value="${param.patfname}">
      <input type="hidden" name="patlname" value="${param.patlname}">
      <input type="hidden" name="hokenmei" value="${param.hokenmei}">
      <input type="hidden" name="currentHokenexp" value="${param.currentHokenexp}">
      <input type="hidden" name="newHokenmei" value="${param.newHokenmei}">
      <input type="hidden" name="newHokenexp" value="${param.newHokenexp}">
      <input type="submit" name="action" value="変更">
      <input type="submit" name="action" value="編集">
      <input type="submit" name="action" value="キャンセル">
    </form>
  </body>
</html>
