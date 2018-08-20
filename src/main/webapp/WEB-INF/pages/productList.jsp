<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<p>
  Hello from product list!
</p>
<table>
  <thead>
    <tr>
      <td>Id</td>
      <td>Code</td>
      <td>Description</td>
      <td>Price</td>
      <td>Currency</td>
      <td>Stock</td>
    </tr>
  </thead>
  <c:forEach var="product" items="${products}">
    <tr>
      <td>${product.getId()}</td>
      <td>${product.getCode()}</td>
      <td>${product.getDescription()}</td>
      <td>${product.getPrice()}</td>
      <td>${product.getCurrency()}</td>
      <td>${product.getStock()}</td>
    </tr>
  </c:forEach>
</table>