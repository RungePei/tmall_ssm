<%@ page contentType="text/html; charset=UTF-8" language="java"
    pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<html>
<head>
    <title>list</title>
</head>
<body>
<table align="center" border="1">
    <tr>
        <td>ID</td>
        <td>图片</td>
        <td>分类名称</td>
        <td>属性管理</td>
        <td>产品管理</td>
        <td>编辑</td>
        <td>删除</td>
    </tr>
    <c:forEach items="${cs}" var="c">
        <tr>
            <td>${c.id}</td>
            <td><img height="40px" src="img/category/${c.id}.jpg" alt=""></td>
            <td>${c.name}</td>
            <td><a href="/admin_property_list?cid=${c.id}"><span class="glyphicon glyphicon-th-list"></span></a></td>
            <td><a href="/admin_product_list?cid=${c.id}"><span class="glyphicon glyphicon-shopping-cart"></span></a></td>
            <td><a href="updateCategory.jsp?id=${c.id}"><span class="glyphicon glyphicon-edit"></span></a></td>
            <td><a id="deleteCategory" href="/admin_category_delete?id=${c.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
        </tr>
    </c:forEach>
    <div align="center">
        <a href="?start=0"><<</a>
        <a href="?start=${page.start-page.count}"><</a>
        <a><fmt:formatNumber type="number" value="${(page.start/page.count)+1}" maxFractionDigits="0"/></a>
        <a href="?start=${page.start+page.count}">></a>
        <a href="?start=${page.last}">>></a>
    </div>
</table>

<form id="addForm" action="/admin_category_add" method="post" enctype="multipart/form-data">
    <div align="center">
    分类名称<input type="text" name="name" id="name">
    分类图片<input type="file" name="image" id="CatePic" accept="image/*">
    <input type="submit" value="提交">
    </div>
</form>
</body>
<script>
    $(function(){
        $("#addForm").submit(function(){
            if (!checkEmpty("name","分类名称"))
                return false;
            if (!checkEmpty("CatePic","分类图片"))
                return false;
            return true;
        });
    });

    $(function(){
        $("#deleteCategory").click(function () {
            console.log("删除");
            var res=confirm("确认要删除？");
            if (res)
                return true;
            return false;
        });
    });
</script>
</html>
