<<<<<<< HEAD
<jsp:useBean id="product" scope="request" type="com.es.phoneshop.model.Product"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="quantity" scope="request" class="java.lang.String"/>
<%@ page import="com.es.phoneshop.model.Product" %>
<%@ page import="com.es.phoneshop.model.ProductDao" %>
<%@ page import="com.es.phoneshop.model.ArrayListProductDao" %>
<%@ page import="com.es.phoneshop.exception.ProductNotFoundException" %>
<%@ page import="com.es.phoneshop.web.ProductDetailsPageServlet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
=======
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="product" scope="request" type="com.es.phoneshop.model.classes.Product"/>

<jsp:useBean id="messageCode" scope="session" type="java.lang.Integer"/>
<jsp:useBean id="quantity" scope="session" class="java.lang.String"/>

<jsp:useBean id="DEFAULT_CODE" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="SUCCESS" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="SUCCESS_HEAD" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="ERROR_HEAD" scope="request" type="java.lang.Integer"/>

<c:set var="locale" value="${pageContext.request.locale}"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

>>>>>>> Task_2.5
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
    <title>Product Details</title>
    <%@include file="../common/header.jsp"%>
</head>
<body>
<<<<<<< HEAD
<c:if test="${message != null}">
<c:choose>
    <c:when test="${message.contains(ProductDetailsPageServlet.SUCCESS_MESSAGE)}">
        <div class="w3-panel w3-green w3-display-container">
            <span onclick="this.parentElement.style.display='none'" class="w3-button w3-green w3-large w3-display-topright">&times;</span>
            <h3>Success</h3>
            <p>${message}</p>
        </div>
    </c:when>
    <c:otherwise>
        <c:if test="${!message.equals('')}">
            <div class="w3-panel w3-red w3-display-container">
            <span onclick="this.parentElement.style.display='none'" class="w3-button w3-red w3-large w3-display-topright">&times;</span>
            <h3>Error</h3>
            <p>${message}</p>
=======
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
<c:if test="${messageCode ne DEFAULT_CODE}">
<c:choose>
    <c:when test="${messageCode eq SUCCESS}">
        <div class="w3-panel w3-green w3-display-container">
            <span onclick="this.parentElement.style.display='none'" class="w3-button w3-green w3-large w3-display-topright">&times;</span>
            <h3><fmt:message key="${SUCCESS_HEAD}" bundle="${lang}"/></h3>
            <p><fmt:message key="${SUCCESS}" bundle="${lang}"/></p>
        </div>
    </c:when>
    <c:otherwise>
        <c:if test="${messageCode ne DEFAULT_CODE}">
            <div class="w3-panel w3-red w3-display-container">
            <span onclick="this.parentElement.style.display='none'" class="w3-button w3-red w3-large w3-display-topright">&times;</span>
                <h3><fmt:message key="${ERROR_HEAD}" bundle="${lang}"/></h3>
                    <p><fmt:message key="${messageCode}" bundle="${lang}"/></p>
>>>>>>> Task_2.5
            </div>
        </c:if>
    </c:otherwise>
</c:choose>
</c:if>
<form method="post">
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
<<<<<<< HEAD
            <input class="w3-button w3-teal" type="submit" value="Add to Cart">
=======
            <input class="w3-button w3-teal w3-round-xxlarge" type="submit" value="Add to Cart">
>>>>>>> Task_2.5
        </th>
    </tr>
    <tr>
        <td>${product.id}</td>
        <td>${product.code}</td>
        <td>${product.description}</td>
        <td>${product.price}</td>
        <td>${product.currency}</td>
        <td>${product.stock}</td>
        <td>
            <label>
<<<<<<< HEAD
                <input type="text" name="quantity" id="inputQuantity" autofocus required value="${quantity.equals('1') || quantity.equals('') ? 1 : quantity}" style="text-align: right">
=======
                <input type="text" name="quantity" id="inputQuantity" autofocus value="${empty quantity ? 1 : quantity}" style="text-align: right">
>>>>>>> Task_2.5
            </label>
        </td>
        <td></td>
    </tr>
</table>
</form>
<%@include file="../common/footer.jsp"%>
</body>
</html>