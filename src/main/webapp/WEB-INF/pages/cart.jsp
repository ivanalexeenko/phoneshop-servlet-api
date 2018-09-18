<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<jsp:useBean id="cart" type = "com.es.phoneshop.model.classes.Cart" scope = "request"/>
<jsp:useBean id="errors" class="java.util.ArrayList" scope="session"/>

<c:set var="locale" value="${pageContext.request.locale}"/>

<script>
    function setCaretPosition(element,index) {
        if(element.setSelectionRange) {
            element.focus();
            element.setSelectionRange(index,index);
        }
        else if(element.createTextRange) {
            var range = element.createTextRange;
            range.collapse(true);
            range.moveEnd('character',index);
            range.moveStart('character',index);
            range.select();
        }
    }
</script>
<script>
    window.onload = function () {
        var input = document.getElementById("inputQuantity");
        setCaretPosition(input,input.value.length);
    }
</script>
<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Your Cart Page</title>
    <%@include file="../common/header.jsp"%>
</head>
<body>
<fmt:setBundle basename = "i18n.messages_RU" var = "russianLang"/>
<fmt:setBundle basename = "i18n.messages" var="englishEnglandLang"/>
<c:choose>
    <c:when test="${locale == 'ru'}">
        <c:set var="lang" value="${russianLang}"/>
    </c:when>
    <c:otherwise>
        <c:set var="lang" value="${englishEnglandLang}"/>
    </c:otherwise>
</c:choose>
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
            <form method="post" name="quantities">
            <table class="w3-table-all w3-hoverable w3-centered">
                <tr class="w3-indigo">
                <th>Id</th>
                    <th>Code</th>
                    <th>Price</th>
                    <th>Currency</th>
                    <th>Stock</th>
                    <th>Quantity</th>
                    <th></th>
                </tr>
                <c:forEach var="cartItem" items="${cart.cartItems}" varStatus="status">
                    <tr>
                    <td><a href="<c:url value="/products/product/${cartItem.product.id}"/>">${cartItem.product.id}</a></td>
                        <td>${cartItem.product.code}</td>
                        <td>${cartItem.product.price}</td>
                        <td>${cartItem.product.currency}</td>
                        <td>${cartItem.product.stock}</td>
                        <td>
                            <input type="hidden" name="productId" value="${cartItem.product.id}">
                            <label>
                                <input type="text" name="newQuantity" id="inputQuantity" value="${cartItem.quantity}" autofocus style="text-align: right">
                            </label>
                            <c:if test="${not empty errors[status.index]}">
                                <div class="w3-panel w3-red w3-small w3-display-container">
                                    <span onclick="this.parentElement.style.display='none'" class="w3-button w3-hover-pink w3-red w3-small w3-display-topright">&times;</span>
                                    <p><fmt:message key="${errors[status.index]}" bundle="${lang}"/></p>
                                </div>
                            </c:if>
                        </td>
                        <td>DELETE</td>
                    </tr>
                </c:forEach>
                <tr class="w3-hover-none">
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td><input type="submit" class="w3-button w3-teal w3-round-xxlarge w3-xlarge" value="Update"></td>
                </tr>
            </table>
            </form>
        </c:otherwise>
        </c:choose>
<%@include file="../common/footer.jsp"%>
</body>
<script type="text/javascript">
    function removeCartItem(productId) {
        var form = document.forms["removeForm"];
        var hiddenInput = form.productId;
        hiddenInput.value = productId;
        form.submit();
        return false;
    }
</script>
</html>