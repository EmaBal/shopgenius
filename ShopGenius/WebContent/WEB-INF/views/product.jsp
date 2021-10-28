<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head><%@ page isELIgnored="false" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product details</title>
<link href="<c:url value="/WEB-INF/css/bootstrap.min.css" />"
 rel="stylesheet">
<script src="<c:url value="/WEB-INF/js/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/WEB-INF/js/bootstrap.min.js" />"></script>
</head>
<body>
 <div class="container">
  <div class="col-md-offset-1 col-md-10">
   <h2>${product.name}</h2>
   <hr />
   <div class="panel panel-info">
    <div class="panel-body">
    <form action="favorites/add">
     <table class="table table-striped table-bordered">
      <tr>
       <td>Name:</td>
       <td>${product.name}</td>
       
      </tr>
      <tr>
       <td>Price:</td>
       <td>${product.price} €</td>
      </tr>
      <tr>
       <td>Available quantity:</td>
       <td>${product.quantity}</td>
      </tr>
      <tr>
       <td>Product type:</td>
       <td>${productType}</td>
      </tr>
      <tr>
       <td>Location detail:</td>
       <td>${product.locationDetail}</td>
      </tr>

     </table>
     <input type="hidden" name="productName" value="${product.name}"></input>
	<input type="submit" value="Add to Favorites" /><br/>
	</form>
    </div>
   </div>
   
  </div>

 </div>
</body>
</html>