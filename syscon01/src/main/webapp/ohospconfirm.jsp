<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>他病院情報確認</title>
  </head>
  <body>
    <h2>他病院情報確認</h2>
    <form action="OhospRegisterServlet" method="post">
      <!-- 登録処理へ進むためのパラメータ -->
      <input type="hidden" name="action" value="register">
      <input type="hidden" name="tabyouinid" value="${tabyouinid}">
      <input type="hidden" name="tabyouinmei" value="${tabyouinmei}">
      <input type="hidden" name="tabyouinaddress" value="${tabyouinaddress}">
      <input type="hidden" name="tabyouintel" value="${tabyouintel}">
      <input type="hidden" name="tabyouinshihonkin" value="${tabyouinshihonkin}">
      <input type="hidden" name="kyukyu" value="${kyukyu}">
      <table border="1">
        <tr>
          <td>他病院ID</td>
          <td>${tabyouinid}</td>
        </tr>
        <tr>
          <td>他病院名</td>
          <td>${tabyouinmei}</td>
        </tr>
        <tr>
          <td>他病院住所</td>
          <td>${tabyouinaddress}</td>
        </tr>
        <tr>
          <td>他病院電話番号</td>
          <td>${tabyouintel}</td>
        </tr>
        <tr>
          <td>資本金</td>
          <td>${tabyouinshihonkin}</td>
        </tr>
        <tr>
          <td>救急</td>
          <td>
            <c:choose>
              <c:when test="${kyukyu == '1'}">救急</c:when>
              <c:otherwise>非救急</c:otherwise>
            </c:choose>
          </td>
        </tr>
      </table>
      <br>
      <input type="submit" value="登録">
      <input type="button" value="キャンセル" onclick="location.href='mainPage.jsp'">
    </form>
  </body>
</html>
