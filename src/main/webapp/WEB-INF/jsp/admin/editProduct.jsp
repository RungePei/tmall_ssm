<%@ page contentType="text/html; charset=UTF-8" language="java"
         pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<html>
<head>
    <title>edit</title>
</head>
<body>
<form action="/admin_product_update" method="post">
    <table align="center">
        <tr><td><input type="hidden" id="productCid" name="cid" value="${product.cid}"></td></tr>
        <tr><td><input type="hidden" id="productId" name="id" value="${product.id}"></td></tr>
        <tr>
            <td>名称</td>
            <td><input type="text" id="productName" name="name" placeholder="${product.name}"></td>
        </tr>
        <tr>
            <td>标题</td>
            <td><input type="text" id="productTitle" name="subTitle" placeholder="${product.subTitle}"></td>
        </tr>
        <tr>
            <td>原始价格</td>
            <td><input type="text" id="productPr" name="orignalPrice" placeholder="${product.orignalPrice}"></td>
        </tr>
        <tr>
            <td>优惠价格</td>
            <td><input type="text" id="productPri" name="promotePrice" placeholder="${product.promotePrice}"></td>
        </tr>
        <tr>
            <td>库存</td>
            <td><input type="text" id="productSto" name="stock" placeholder="${product.stock}"></td>
        </tr>
        <tr>
            <td><input type="submit" value="提交"></td>
        </tr>
    </table>
</form>
</body>
</html>
