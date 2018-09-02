<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id = "cart" type = "com.es.phoneshop.model.Cart" scope = "request"/>
<html>
<head>
    <title>Your Cart Page</title>
    <%@include file="../common/header.jsp"%>
</head>
<body>
        <c:choose>
    <c:when test="${cart.cartItems.isEmpty()}">
        <div style="text-align: center">
            <b>Your Cart is Empty, try to add something and come back =)</b>
        </div>
    </c:when>
        <c:otherwise>
            <b>Products added to cart:</b>
            <table>
                <tr>
                <th>Id</th>
                    <th>Code</th>
                    <th>Price</th>
                    <th>Currency</th>
                <th>Quantity</th>
                </tr>
                <c:forEach var="cartItem" items="${cart.cartItems}">
                    <tr>
                    <td><a href="<c:url value="/products/product/${cartItem.product.id}"/>">${cartItem.product.id}</a></td>
                        <td>${cartItem.product.code}</td>
                        <td>${cartItem.product.price}</td>
                        <td>${cartItem.product.currency}</td>
                    <td>${cartItem.quantity}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
        </c:choose>
<%@include file="../common/footer.jsp"%>
</body>
</html>