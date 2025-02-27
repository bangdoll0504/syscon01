<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>メインページ</title>
</head>
<body>
    <h2>メインページ</h2>
    <p>ようこそ、${userId}さん！</p>
    <ul class="menu">
      <li><a href="empReg.jsp">従業員登録</a></li>
      <li><a href="emp_P_input.jsp">従業員パスワード変更（管理者向け）</a></li>
      <li><a href="emp_P_input.jsp">従業員パスワード変更（ログイン対象者向け）</a></li>
      <li><a href="emp_n_input.jsp">従業員氏名情報情報変更</a></li>
      <li><a href="suppreg.jsp">仕入れ業者登録</a></li>
      <li><a href="supplList.jsp">仕入れ先一覧・検索</a></li>
      <li><a href="supplTelSearch.jsp">仕入れ先電話番号変更</a></li>
      <li><a href="ohospreg.jsp">他病院登録</a></li>
      <li><a href="ohosplist.jsp">他病院一覧・検索</a></li>
      <li><a href="ohosptelsearch.jsp">他病院電話番号変更</a></li>
      <li><a href="patreg.jsp">患者登録</a></li>
      <li><a href="patientList.jsp">患者一覧・検索</a></li>
      <li><a href="patsearch.jsp">保険証情報変更</a></li>
      <li><a href="medreg.jsp">処方薬登録</a></li>
      <li><a href="pill_patsearch.jsp">処方</a></li>
    </ul>
</body>
</html>
