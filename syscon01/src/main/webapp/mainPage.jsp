<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>Main Page - 管理者ページ</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body class="main-page">
  <!-- ヘッダー -->
  <header class="main-header">
    <nav>
      <!-- ul要素で構成されたメニュー（指定の部分） -->
      <ul class="menu">
        <li><a href="empReg.jsp">従業員登録</a></li>
        <li><a href="emp_P_input.jsp">従業員パスワード変更（管理者向け）</a></li>
        <li><a href="changePassword.jsp">従業員パスワード変更（ログイン対象者向け）</a></li>
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
        <li><a href="patdrugsearch.jsp">処方結果一覧</a></li>
      </ul>
    </nav>
  </header>
  
  <!-- メインコンテンツ -->
  <main class="main-content">
    <section class="welcome-section">
      <h1>ようこそ、<c:out value="${userId}" />さん</h1>
      <p>管理者向けのダッシュボードへようこそ。上記メニューから操作を選択してください。</p>
    </section>
  </main>
  
  <!-- フッター -->
  <footer class="main-footer">
    <p>&copy; 2025 Modern Web Design. All rights reserved.</p>
  </footer>
</body>
</html>
