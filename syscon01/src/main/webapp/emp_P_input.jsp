<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>パスワード変更 - 従業員ID入力</title>
</head>
<body>
    <h2>従業員パスワード変更</h2>
    <%-- エラー表示（該当従業員が存在しない場合など） --%>
    <c:if test="${not empty errorMsg}">
        <p style="color:red;">${errorMsg}</p>
    </c:if>
    <form action="EmployeePasswordChangeServlet" method="post">
        <label for="employeeId">従業員ID:</label>
        <input type="text" name="employeeId" id="employeeId" required/>
        <!-- アクション識別用 hidden -->
        <input type="hidden" name="action" value="checkEmployee"/>
        <br/>
        <input type="submit" value="検索"/>
    </form>
</body>
</html>
