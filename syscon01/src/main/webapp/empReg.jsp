<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>従業員登録</title>
</head>
<body>
    <div class="container">
        <h2>従業員登録</h2>
        <form action="empconfirm.jsp" method="post">
            <label>従業員ID (empId): <input type="text" name="empId" required></label><br>
            <label>姓 (emplname): <input type="text" name="emplname" required></label><br>
            <label>名 (empfname): <input type="text" name="empfname" required></label><br>
            <label>パスワード (emppassword): <input type="password" name="emppassword" required></label><br>
            <label>ロール (emprole):
                <select name="emprole">
                    <option value="0">管理者</option>
                    <option value="1">受付</option>
                    <option value="2">医師</option>
                </select>
            </label><br>
            <div class="buttons">
                <input type="submit" value="確認">
                <button type="button" onclick="location.href='mainMenu.jsp'">キャンセル</button>
            </div>
        </form>
    </div>
</body>
</html>
