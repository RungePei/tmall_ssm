<%@ page contentType="text/html; charset=UTF-8" language="java"
         pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<html>
<head>
    <title>编辑</title>
</head>
<body>
<form action="/admin_property_update" method="post">
    <table>
        <tr>
            <td><input type="hidden" name="id" value="${p.id}"></td>
            <td><input type="hidden" name="cid" value="${p.cid}"></td>
            <td><input type="text" name="name" id="pro_update_name" placeholder="${p.name}"></td>
        </tr>
        <tr>
            <td><input type="submit" value="提交"></td>
        </tr>
    </table>
</form>
</body>
</html>
