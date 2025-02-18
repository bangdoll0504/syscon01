<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>仕入先情報確認</title>
  </head>
  <body>
    <h2>仕入先情報確認</h2>
    <form action="SuppRegServlet" method="post">
      <!-- 登録処理に進むためのパラメータ -->
      <input type="hidden" name="action" value="register">
      <!-- 入力内容をhiddenで保持 -->
      <input type="hidden" name="shiireid" value="${shiireid}">
      <input type="hidden" name="shiiremei" value="${shiiremei}">
      <input type="hidden" name="shiireaddress" value="${shiireaddress}">
      <input type="hidden" name="shiiretel" value="${shiiretel}">
      <input type="hidden" name="shihonkin" value="${shihonkin}">
      <input type="hidden" name="nouki" value="${nouki}">
      
      <table border="1">
        <tr>
          <td>仕入先ID</td>
          <td>${shiireid}</td>
        </tr>
        <tr>
          <td>仕入先名</td>
          <td>${shiiremei}</td>
        </tr>
        <tr>
          <td>仕入先住所</td>
          <td>${shiireaddress}</td>
        </tr>
        <tr>
          <td>仕入先電話番号</td>
          <td>${shiiretel}</td>
        </tr>
        <tr>
          <td>資本金</td>
          <td>${shihonkin}</td>
        </tr>
        <tr>
          <td>発注日数</td>
          <td>${nouki}</td>
        </tr>
      </table>
      <br>
      <input type="submit" value="登録">
      <input type="button" value="キャンセル" onclick="location.href='mainPage.jsp'">
    </form>
  </body>
</html>
