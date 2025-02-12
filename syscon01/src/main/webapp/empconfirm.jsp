<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>登録確認</title>
</head>
<body>
    <div class="container">
        <h2>登録内容の確認</h2>
        <table>
            <tr><th>従業員ID:</th><td>${param.empId}</td></tr>
            <tr><th>姓:</th><td>${param.emplname}</td></tr>
            <tr><th>名:</th><td>${param.empfname}</td></tr>
            <tr><th>ロール:</th>
                <td>
                    <c:choose>
                        <c:when test="${param.emprole == '0'}">管理者</c:when>
                        <c:when test="${param.emprole == '1'}">受付</c:when>
                        <c:when test="${param.emprole == '2'}">医師</c:when>
                    </c:choose>
                </td>
            </tr>
        </table>

        <form action="empregister" method="post">
            <input type="hidden" name="empId" value="${param.empId}">
            <input type="hidden" name="emplname" value="${param.emplname}">
            <input type="hidden" name="empfname" value="${param.empfname}">
            <input type="hidden" name="emppassword" value="${param.emppassword}">
            <input type="hidden" name="emprole" value="${param.emprole}">
            <div class="buttons">
                <input type="submit" value="OK">
                <button type="button" onclick="history.back()">修正</button>
                <button type="button" onclick="location.href='mainMenu.jsp'">キャンセル</button>
            </div>
        </form>
    </div>
</body>
</html>
