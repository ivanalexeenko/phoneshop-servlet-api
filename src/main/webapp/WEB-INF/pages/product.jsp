<jsp:useBean id="product" scope="request" type="com.es.phoneshop.model.Product"/>
<%@ page import="com.es.phoneshop.model.Product" %>
<%@ page import="com.es.phoneshop.model.ProductDao" %>
<%@ page import="com.es.phoneshop.model.ArrayListProductDao" %>
<%@ page import="com.es.phoneshop.exception.ProductNotFoundException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product Details</title>
    <%@include file="../common/header.jsp"%>
</head>
<body>
<table class="w3-table-all w3-hoverable w3-centered">
    <tr class="w3-indigo">
        <th>Id</th>
        <th>Code</th>
        <th>Description</th>
        <th>Price</th>
        <th>Currency</th>
        <th>Stock</th>
    </tr>
    <tr>
        <td>${product.id}</td>
        <td>${product.code}</td>
        <td>${product.description}</td>
        <td>${product.price}</td>
        <td>${product.currency}</td>
        <td>${product.stock}</td>
    </tr>
</table>
<%@include file="../common/footer.jsp"%>
</body>
</html>
