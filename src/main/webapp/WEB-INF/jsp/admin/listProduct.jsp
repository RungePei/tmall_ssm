<%@ page contentType="text/html; charset=UTF-8" language="java"
         pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>
<html>
<head>
    <title>listProduct</title>
</head>
<body>
<table align="center" border="1">
    <tr>
        <td>ID</td>
        <td>图片</td>
        <td>名称</td>
        <td>标题</td>
        <td>原价格</td>
        <td>优惠价格</td>
        <td>库存数量</td>
        <td>图片管理</td>
        <td>设置属性</td>
        <td>编辑</td>
        <td>删除</td>
    </tr>
    <c:forEach items="${list}" var="p">
        <tr>
            <td>${p.id}</td>
            <td>

                    <%--<c:if test="${!empty p.firstProductImage}">--%>
                    <%--<img width="40px" src="img/productSingle/${p.firstProductImage.id}.jpg">--%>
                    <%--</c:if>--%>

            </td>
            <td>${p.name}</td>
            <td>${p.subTitle}</td>
            <td>${p.orignalPrice}</td>
            <td>${p.promotePrice}</td>
            <td>${p.stock}</td>
            <td><a href="admin_productImage_list?pid=${p.id}"><span
                    class="glyphicon glyphicon-picture"></span></a></td>
            <td><a href="admin_propertyValue_edit?pid=${p.id}"><span
                    class="glyphicon glyphicon-th-list"></span></a></td>

            <td><a href="admin_product_edit?id=${p.id}"><span
                    class="glyphicon glyphicon-edit"></span></a></td>
            <td><a deleteLink="true"
                   href="admin_product_delete?id=${p.id}"><span
                    class="     glyphicon glyphicon-trash"></span></a></td>

        </tr>
    </c:forEach>
</table>
<form method="post" action="/admin_product_add">
    <table align="center" border="1">
        <tr><td><input type="hidden" id="productCid" name="cid" value="${category.id}"></td></tr>
        <tr>
            <td>名称</td>
            <td><input type="text" id="productName" name="name" value="产品"></td>
        </tr>
        <tr>
            <td>标题</td>
            <td><input type="text" id="productTitle" name="subTitle" value="买到就是赚到"></td>
        </tr>
        <tr>
            <td>原始价格</td>
            <td><input type="text" id="productPr" name="orignalPrice" value="100"></td>
        </tr>
        <tr>
            <td>优惠价格</td>
            <td><input type="text" id="productPri" name="promotePrice" value="99.9"></td>
        </tr>
        <tr>
            <td>库存</td>
            <td><input type="text" id="productSto" name="stock" value="10"></td>
        </tr>
        <tr>
            <td><input type="submit" value="提交"></td>
        </tr>
    </table>
</form>
</body>
</html>
