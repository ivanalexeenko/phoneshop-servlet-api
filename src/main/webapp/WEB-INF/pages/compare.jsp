1<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="comparisonList" scope="session" class="java.util.ArrayList"/>

<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Comparison Page</title>
    <%@include file="../common/header.jsp" %>
</head>
<body>
<c:choose>
    <c:when test="${empty comparisonList}">
        <div style="text-align: center">
            <b style="text-shadow:1px 1px 0 #444">OOps,nothing to compare here =)</b>
        </div>
    </c:when>
    <c:otherwise>
        <table class="w3-table-all w3-hoverable w3-centered w3-border-pale-blue">
            <tr class="w3-indigo">
                <th>Id</th>
                <th>Code</th>
                <th>Description</th>
                <th>Price</th>
                <th>Currency</th>
                <th>Stock</th>
                <th>Quantity</th>
                <th>
                    <input class="w3-button w3-teal w3-round-xxlarge" name="add" type="submit" value="Add to Cart">
                </th>
                <th>
                    <input class="w3-button w3-teal w3-round-xxlarge" name="compare" type="submit" value="Add For Comparison">
                </th>
            </tr>
            <c:forEach var="product" items="${comparisonList}">
                <tr>
                    <td><a href="<c:url value="/products/product/${product.id}"/>">${product.id}</a></td>
                    <td>${product.code}</td>
                    <td>${product.description}</td>
                    <td>${product.price}</td>
                    <td>${product.currency}</td>
                    <td>${product.stock}</td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
<%@include file="../common/footer.jsp" %>
</body>
</html>

