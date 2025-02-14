<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
    String employeeId = (String) request.getAttribute("employeeId");
    String role = (String) request.getAttribute("role");
    String errorMsg = (String) request.getAttribute("errorMsg");
%>
<html>
<head>
    <title>パスワード変更 - 新パスワード入力</title>
</head>
<body>
    <h2>パスワード変更</h2>
    <p>従業員ID: <%= employeeId %></p>
    <% if (errorMsg != null) { %>
        <p style="color:red;"><%= errorMsg %></p>
    <% } %>
    <form action="EmployeePasswordChangeServlet" method="post">
        <label for="password1">新パスワード:</label>
        <input type="password" name="password1" id="password1" required/><br/>
        <label for="password2">新パスワード（確認）:</label>
        <input type="password" name="password2" id="password2" required/><br/>
        <!-- 後続で利用するため、従業員IDとロールをhiddenで渡す -->
        <input type="hidden" name="employeeId" value="<%= employeeId %>"/>
        <input type="hidden" name="role" value="<%= role %>"/>
        <input type="hidden" name="action" value="validatePasswords"/>
        <input type="submit" value="更新"/>
        <input type="button" value="キャンセル" onclick="location.href='mainPage.jsp'"/>
    </form>
</body>
</html>
