<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<jsp:useBean id="total" type="java.math.BigDecimal" scope="request"/>
<jsp:useBean id="currency" type="java.util.Currency" scope="request"/>
<jsp:useBean id="bundleNames" type="java.lang.String[]" scope="request"/>
<jsp:useBean id="errorHead" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="error" type="java.lang.Integer" scope="request"/>

<jsp:useBean id="isError" type="java.lang.Boolean" scope="session"/>

<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Checkout Page</title>
    <%@include file="../common/header.jsp" %>
</head>
<body>
<fmt:setBundle basename="i18n.messages" var="lang"/>
<c:choose>
    <c:when test="${cart.cartItems.isEmpty()}">
        <div style="text-align: center">
            <b style="text-shadow:1px 1px 0 #444">Your Cart is Empty, try to add something and come back =)</b>
        </div>
    </c:when>
    <c:otherwise>
        <c:if test="${isError}">
            <div class="w3-panel w3-red w3-small w3-display-container">
        <span onclick="this.parentElement.style.display='none'"
              class="w3-button w3-hover-pink w3-red w3-small w3-display-topright">&times;</span>
                <h3>
                    <fmt:message key="${bundleNames[errorHead]}" bundle="${lang}"/>
                </h3>
                <p><fmt:message key="${bundleNames[error]}" bundle="${lang}"/></p>
            </div>
        </c:if>
        <div class="w3-container">
        <div class="w3-card-4">
            <div class="w3-container w3-teal">
                <h2>Place Order</h2>
            </div>
            <form method="post" class="w3-container w3-">
                <p>
                    <label class="w3-text-black"><b>Name:</b>
                        <input class="w3-input w3-border" name="name" type="text">
                    </label>
                </p>
                <p>
                    <label class="w3-text-black"><b>Address:</b>
                        <input class="w3-input w3-border" name="address" type="text">
                    </label>
                </p>
                <p>
                    <label class="w3-text-black"><b>Phone:</b>
                        <input class="w3-input w3-border" name="phone" type="text">
                    </label>
                </p>
                <input class="w3-left w3-button w3-teal w3-xlarge w3-round-xxlarge" type="submit" value="Place">
            </form>
        </div>
<div class="w3-card-4">
        <div style="text-align: center">
            <b style="text-shadow:1px 1px 0 #444">Products added to cart:</b>
        </div>
            <table class="w3-table-all w3-hoverable w3-centered">
                <tr class="w3-indigo">
                    <th>Id</th>
                    <th>Code</th>
                    <th>Price</th>
                    <th>Quantity</th>
                </tr>
                <c:forEach var="cartItem" items="${cart.cartItems}" varStatus="status">
                    <tr>
                        <td>
                            <a href="<c:url value="/products/product/${cartItem.product.id}"/>">${cartItem.product.id}</a>
                        </td>
                        <td>${cartItem.product.code}</td>
                        <td>${cartItem.product.price} ${cartItem.product.currency}</td>
                        <td>${cartItem.quantity}</td>
                    </tr>
                </c:forEach>
                <tr class="w3-black w3-text-white">
                <td>
                </td>
                <td></td>
                <th>Total cost = ${total} ${currency}</th>
                <td></td>
                </tr>
            </table>
</div>
        </div>
    </c:otherwise>
</c:choose>
<%@include file="../common/footer.jsp" %>
</body>
</html>