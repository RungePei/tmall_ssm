<%@ page contentType="text/html; charset=UTF-8" language="java"
         pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>
<html>
<head>
    <title>ProductImage</title>
</head>
<body>
<table border="1" align="center">
    <tr><td>单个</td></tr>
    <tr>
        <td>ID</td>
        <td>图片</td>
        <td>删除</td>
    </tr>
    <c:forEach items="${piSingle}" var="p">
        <tr>
            <td>${p.id}</td>
            <td><img src="img/productImageSingle/${p.id}.jpg" height="50px"></td>
            <td><a href="/admin_productImage_delete?id=${p.id}"><span class="glyphicon glyphicon-trash">
                </span></a></td>
        </tr>
    </c:forEach>
</table>
<form method="post" enctype="multipart/form-data" action="/admin_productImage_add">
    <table align="center" border="1">
        <tr><td>新增单个图片</td></tr>
        <tr>
            <td><input type="hidden" id="pro_img_pid_sin" name="pid" value="${product.id}"></td>
            <td><input type="hidden" id="pro_img_type_sin" name="type" value="type_single"></td>
        </tr>
        <tr><td><input type="file" accept="image/*" name="image" id="pro_img_sin"></td></tr>
        <tr><td><input type="submit" value="提交"></td></tr>
    </table>
</form>
<table border="1" align="center">
    <tr><td>详细</td></tr>
    <tr>
        <td>ID</td>
        <td>图片</td>
        <td>删除</td>
    </tr>
    <c:forEach items="${piDetail}" var="p">
        <tr>
            <td>${p.id}</td>
            <td><img src="img/productImageDetail/${p.id}.jpg" height="50px"></td>
            <td><a href="/admin_productImage_delete?id=${p.id}"><span class="glyphicon glyphicon-trash">
                </span></a></td>
        </tr>
    </c:forEach>
</table>
<form method="post" enctype="multipart/form-data" action="/admin_productImage_add">
    <table align="center" border="1">
        <tr><td>新增详情图片</td></tr>
        <tr>
            <td><input type="hidden" id="pro_img_pid" name="pid" value="${product.id}"></td>
            <td><input type="hidden" id="pro_img_type" name="type" value="type_detail"></td>
        </tr>
        <tr><td><input type="file" accept="image/*" name="image" id="pro_img_det"></td></tr>
        <tr><td><input type="submit" value="提交"></td></tr>
    </table>
</form>
</body>
</html>
