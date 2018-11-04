<%@ page contentType="text/html; charset=UTF-8" language="java"
         pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<html>
<head>
    <title>listUser</title>
</head>
<body>
<table align="center" border="1">
    <tr>
        <td>id</td>
        <td>name</td>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
