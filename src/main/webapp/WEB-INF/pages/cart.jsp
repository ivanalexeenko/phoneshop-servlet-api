<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id = "cart" type = "com.es.phoneshop.model.classes.Cart" scope = "request"/>
<html>
<head>
    <title>Your Cart Page</title>
    <%@include file="../common/header.jsp"%>
</head>
<body>
        <c:choose>
    <c:when test="${cart.cartItems.isEmpty()}">
        <div style="text-align: center">
            <b style="text-shadow:1px 1px 0 #444">Your Cart is Empty, try to add something and come back =)</b>
        </div>
    </c:when>
        <c:otherwise>
            <div style="text-align: center">
                <b style="text-shadow:1px 1px 0 #444">Products added to cart:</b>
            </div>
            <table class="w3-table-all w3-hoverable w3-centered">
                <tr class="w3-indigo">
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
                    <td>
                        <fmt:formatNumber type="number">
                            ${cartItem.quantity}
                        </fmt:formatNumber>
                    </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
        </c:choose>
<%@include file="../common/footer.jsp"%>
</body>
</html>