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
                // console.log($(this).attr("pvname"));
                // alert($(this).attr("pvname"));
                var value = $(this).val();
                var page = "admin_propertyValue_update";
                var pvid = $(this).attr("pvid");
                var parentSpan = $(this).parent("span");
                parentSpan.css("border", "1px solid yellow");
                $.post(
                    page,
                    {"value": value, "id": pvid},
                    function (result) {
                        if (result == "success")
                            parentSpan.css("border", "2px solid green");
                        else
                            parentSpan.css("border", "2px solid red");
                    }
                );
            });
        });
    </script>
</head>
<body>
<%--<table align="center" border="1">--%>
    <%--<c:forEach items="${pvs}" var="pv">--%>
        <%--<tr>--%>
            <%--<td><input type="text" value="${pv.property.name}"></td>--%>
            <%--<td><input class="pvValue" type="text" pvid="${pv.id}" value="${pv.value}"></td>--%>
        <%--</tr>--%>
    <%--</c:forEach>--%>
<%--</table>--%>
<div class="editPVDiv">
    <c:forEach items="${pvs}" var="pv">
        <div class="eachPV">
            <span class="pvName" >${pv.property.name}</span>
            <span class="pvValue"><input class="pvValue" pvid="${pv.id}" type="text" value="${pv.value}"></span>
        </div>
    </c:forEach>
    <div style="clear:both"></div>
</div>
</body>
</html>
