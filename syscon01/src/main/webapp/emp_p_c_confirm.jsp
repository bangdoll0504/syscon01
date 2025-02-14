<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
    String employeeId = request.getParameter("employeeId");
    String password = request.getParameter("password");
    String role = request.getParameter("role");
%>
<html>
<head>
    <title>パスワード変更確認</title>
</head>
<body>
    <h2>パスワード変更確認</h2>
    <p>従業員ID: <%= employeeId %></p>
    <p>新しいパスワードで更新します。よろしいですか？</p>
    <form action="EmployeePasswordChangeServlet" method="post">
        <!-- 更新対象情報をhiddenで渡す -->
        <input type="hidden" name="employeeId" value="<%= employeeId %>"/>
        <input type="hidden" name="password" value="<%= request.getAttribute("password") %>">
        <input type="hidden" name="role" value="<%= role %>"/>
        <% System.out.println("Debug1: Password = " + request.getAttribute("password")); %>
        <% System.out.println("Debug2: Password = " + password); %>
        <input type="hidden" name="action" value="updatePassword"/>
        <input type="submit" name="confirm" value="変更"/>
        <input type="submit" name="confirm" value="キャンセル"/>
    </form>
</body>
</html>
