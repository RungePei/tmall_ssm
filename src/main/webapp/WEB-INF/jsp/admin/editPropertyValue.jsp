<%@ page contentType="text/html; charset=UTF-8" language="java"
         pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>
<html>
<head>
    <title>editPropertyValue</title>
    <script>
        $(function () {
            $("input.pvValue").keyup(function () {
                var value = $(this).val();
                var page = "admin_propertyValue_update";
                var pvid = $(this).attr("pvid");
                var parentTd = $(this).parent("td");
                parentTd.css("border", "1px solid yellow");
                $.post(
                    page,
                    {"value": value, "id": pvid},
                    function (result) {
                        if (result == "success")
                            parentTd.css("border", "1px solid green");
                        else
                            parentTd.css("border", "1px solid red");
                    }
                );
            });
        });
    </script>
</head>
<body>
<table align="center" border="1">
    <c:forEach items="${pvs}" var="pv">
        <tr>
            <td>${pv.property.name}</td>
            <td><input class="pvValue" type="text" pvid="${pv.id}" value="${pv.value}"></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
