<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>仕入先情報入力</title>
  </head>
  <body>
    <h2>仕入先情報入力</h2>
    <form action="SuppRegServlet" method="post">
      <!-- 処理の切り替え用パラメータ -->
      <input type="hidden" name="action" value="confirm">
      仕入先ID：<input type="text" name="shiireid" required><br><br>
      仕入先名：<input type="text" name="shiiremei"><br><br>
      仕入先住所：<input type="text" name="shiireaddress"><br><br>
      仕入先電話番号：<input type="text" name="shiiretel"><br><br>
      資本金：<input type="number" name="shihonkin"><br><br>
      発注日数：<input type="number" name="nouki"><br><br>
      <input type="submit" value="確認">
      <input type="button" value="キャンセル" onclick="location.href='mainPage.jsp'">
    </form>
  </body>
</html>
