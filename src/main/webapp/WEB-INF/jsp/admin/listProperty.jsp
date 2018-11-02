<%@ page contentType="text/html; charset=UTF-8" language="java"
         pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<html>
<head>
    <title>Property</title>
</head>
<body>
<table align="center" border="1">
    <tr>
        <td>ID</td>
        <td>名称</td>
        <td>编辑</td>
        <td>删除</td>
    </tr>
    <c:forEach items="${ps}" var="p">
        <tr>
            <td>${p.id}</td>
            <td>${p.name}</td>
            <td><a href="/admin_property_edit?id=${p.id}"><span class="glyphicon glyphicon-edit"></span> </a></td>
            <td><a href="/admin_property_delete?id=${p.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
        </tr>
    </c:forEach>
</table>
<div align="center">
    <a href="?start=0${page.param}"> << </a>
    <a href="?start=${page.start-page.count}${page.param}"> < </a>
    <a><fmt:formatNumber type="number" value="${(page.start/page.count)+1}" maxFractionDigits="0"/></a>
    <a href="?start=${page.start+page.count}${page.param}"> > </a>
    <a href="?start=${page.last}${page.param}"> >> </a>
</div>

<form action="/admin_property_add" method="post">
    <table align="center" border="1">
        <tr><td><input type="hidden" name="cid" value="${category.id}"></td></tr>
        <tr>
            <td>名称</td>
            <td><input type="text" id="pro_name" name="name"></td>
        </tr>
        <tr>
            <td><input type="submit" value="提交"></td>
        </tr>
    </table>
</form>
</body>
</html>
