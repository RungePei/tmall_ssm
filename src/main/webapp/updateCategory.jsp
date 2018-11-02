<%@ page contentType="text/html; charset=UTF-8" language="java"
         pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="WEB-INF/jsp/include/admin/adminHeader.jsp" %>
<%@include file="WEB-INF/jsp/include/admin/adminNavigator.jsp" %>
<html>
<head>
    <title>update</title>
</head>
<body>
<form action="/admin_category_update" method="post" enctype="multipart/form-data">
    <div align="center">
        <input type="hidden" name="id" value=<%=request.getParameter("id")%>>
        <div>名称<input id="name" name="name" type="text"></div>
        <div>图片<input id="pic" name="image" type="file" accept="image/*"></div>
        <div><input type="submit" value="修改"></div>
    </div>
</form>
</body>
</html>
