<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head><%@ page isELIgnored="false" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manage Products</title>
<link href="<c:url value="/WEB-INF/css/bootstrap.min.css" />"
 rel="stylesheet">
<script src="<c:url value="/WEB-INF/js/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/WEB-INF/js/bootstrap.min.js" />"></script>
</head>
<body>
 <div class="container">
  <div class="col-md-offset-1 col-md-10">
   <h2>Manage Products</h2>
   <hr />

   <input type="button" value="Add product"
    onclick="window.location.href='add'; return false;"
    class="btn btn-primary" />
    <br/><br/>
   <div class="panel panel-info">
    <div class="panel-body">
     <table class="table table-striped table-bordered">
      <tr>
       <th>Name</th>
       <th>Price</th>
       <th>Quantity</th>
       <th>Type</th>
       <th>Location Detail</th>
      </tr>

      <!-- loop over and print our customers -->
      <c:forEach var="tempProduct" items="${products}">

       <!-- construct an "update" link with customer id -->
       <c:url var="updateLink" value="/product/update">
        <c:param name="productId" value="${tempProduct.id}" />
       </c:url>

       <!-- construct an "delete" link with customer id -->
       <c:url var="deleteLink" value="/product/delete">
        <c:param name="productId" value="${tempProduct.id}" />
       </c:url>

       <tr>
        <td>${tempProduct.name}</td>
        <td>${tempProduct.price}</td>
        <td>${tempProduct.quantity}</td>
        <td>${tempProduct.productType.typeName}</td>
        <td>${tempProduct.locationDetail}</td>

        <td>
         <!-- display the update link --> <a href="${updateLink}">Update</a>
         | <a href="${deleteLink}"
         onclick="if (!(confirm('Are you sure you want to delete this product?'))) return false">Delete</a>
        </td>

       </tr>

      </c:forEach>

     </table>

    </div>
   </div>
  </div>

 </div>
</body>
</html>