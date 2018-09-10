<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="product" scope="request" type="com.es.phoneshop.model.Product"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="quantity" scope="request" class="java.lang.String"/>
<jsp:useBean id="notNumber" scope="request" class="java.lang.String"/>
<jsp:useBean id="lessEqualZero" scope="request" class="java.lang.String"/>
<jsp:useBean id="emptyField" scope="request" class="java.lang.String"/>
<jsp:useBean id="notEnough" scope="request" class="java.lang.String"/>

<c:set var="locale" value="${pageContext.request.locale}"/>
<%@ page import="com.es.phoneshop.model.Product" %>
<%@ page import="com.es.phoneshop.model.ProductDao" %>
<%@ page import="com.es.phoneshop.model.ArrayListProductDao" %>
<%@ page import="com.es.phoneshop.exception.ProductNotFoundException" %>
<%@ page import="com.es.phoneshop.web.ProductDetailsPageServlet" %>
<%@ page import="com.es.phoneshop.bundle.ResourceBundleEnglishEngland" %>
<%@ page import="com.es.phoneshop.bundle.ResourceBundleRussian" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
    <title>Product Details</title>
    <%@include file="../common/header.jsp"%>
</head>
<body>
<fmt:setBundle basename = "com.es.phoneshop.bundle.ResourceBundleRussian" var = "russianLang"/>
<fmt:setBundle basename = "com.es.phoneshop.bundle.ResourceBundleEnglishEngland" var="englishEnglandLang"/>
<c:choose>
    <c:when test="${locale == 'ru'}">
            <c:set var="lang" value="${russianLang}"/>
    </c:when>
    <c:otherwise>
        <c:set var="lang" value="${englishEnglandLang}"/>
    </c:otherwise>
</c:choose>
<c:if test="${message != null}">
<c:choose>
    <c:when test="${message.contains(ProductDetailsPageServlet.SUCCESS_MESSAGE)}">
        <div class="w3-panel w3-green w3-display-container">
            <span onclick="this.parentElement.style.display='none'" class="w3-button w3-green w3-large w3-display-topright">&times;</span>
            <h3><fmt:message key="header.success" bundle="${lang}"/></h3>
            <p><fmt:message key="message.success" bundle="${lang}"/></p>
        </div>
    </c:when>
    <c:otherwise>
        <c:if test="${!message.equals('')}">
            <div class="w3-panel w3-red w3-display-container">
            <span onclick="this.parentElement.style.display='none'" class="w3-button w3-red w3-large w3-display-topright">&times;</span>
                <h3><fmt:message key="header.error" bundle="${lang}"/></h3>
                <c:if test="${message.equals(emptyField)}">
                    <p><fmt:message key="message.empty.field" bundle="${lang}"/></p>
                </c:if>
                <c:if test="${message.equals(lessEqualZero)}">
                    <p><fmt:message key="message.less.equal.zero" bundle="${lang}"/></p>
                </c:if>
                <c:if test="${message.equals(notNumber)}">
                    <p><fmt:message key="message.not.number" bundle="${lang}"/></p>
                </c:if>
                <c:if test="${message.equals(notEnough)}">
                    <p><fmt:message key="message.not.enough" bundle="${lang}"/></p>
                </c:if>
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
            <input class="w3-button w3-teal" type="submit" value="Add to Cart">
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
                <input type="text" name="quantity" id="inputQuantity" autofocus required value="${quantity.equals('1') || quantity.equals('') ? 1 : quantity}" style="text-align: right">
            </label>
        </td>
        <td></td>
    </tr>
</table>
</form>
<%@include file="../common/footer.jsp"%>
</body>
</html>