<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="searchList" class="java.util.ArrayList" scope="session"/>
<jsp:useBean id="search" class="java.lang.String" scope="session"/>
<jsp:useBean id="isSearching" type="java.lang.Boolean" scope="session"/>

<html>
<head>
    <title>List of Products</title>
    <%@ include file="../common/header.jsp" %>
</head>
<body>
<c:choose>
    <c:when test="${!isSearching}">
        <table class="w3-table-all w3-card-4 w3-hoverable w3-centered">
            <tr class="w3-indigo">
                <th>Id</th>
                <th>Code</th>
                <th>Description</th>
                <th>Price</th>
            </tr>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td><a href="<c:url value="/products/product/${product.id}"/>">${product.id}</a></td>
                    <td>${product.getCode()}</td>
                    <td>${product.getDescription()}</td>
                    <td>${product.getPrice()} ${product.getCurrency()}</td>
                </tr>
            </c:forEach>
        </table>
    </c:when>

    <c:otherwise>
        <table class="w3-table-all w3-card-4 w3-hoverable w3-centered">
            <tr class="w3-indigo">
                <th>Id</th>
                <th>Code</th>
                <th>Description</th>
                <th>Price</th>
            </tr>
            <c:forEach var="product" items="${searchList}">
                <tr>
                    <td><a href="<c:url value="/products/product/${product.id}"/>">${product.id}</a></td>
                    <td>${product.getCode()}</td>
                    <td>${product.getDescription()}</td>
                    <td>${product.getPrice()} ${product.getCurrency()}</td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

<form method="post">
    <label>
        <input type="text" name="searchField" id="inputSearch" autofocus
               value="${empty search ? "" : search}" style="text-align: right">
    </label>
    <label>
        <input type="submit" name="searchSubmit" class="w3-button w3-teal w3-hover-red" value="Search">
    </label>
</form>
<%@include file="../common/footer.jsp" %>
</body>
</html>