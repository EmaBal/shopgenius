<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head><%@ page isELIgnored="false" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Favorites</title>
<link href="<c:url value="/WEB-INF/css/bootstrap.min.css" />"
 rel="stylesheet">
<script src="<c:url value="/WEB-INF/js/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/WEB-INF/js/bootstrap.min.js" />"></script>
</head>
<body>
 <div class="container">
  <div class="col-md-offset-1 col-md-10">
   <h2>Favorites List</h2>
   <hr />
   <div class="panel panel-info">
    <div class="panel-body">
    <c:forEach var="favorite" items="${favorites}">
	    <c:url var="viewDetails" value="/product">
	    	<c:param name="productName" value="${favorite.name}" />
	    </c:url>
	    <c:url var="deleteFav" value="/favorites/delete">
	    	<c:param name="productId" value="${favorite.id}" />
	    </c:url>
	    <a href="${viewDetails}">${favorite.name}</a>
 	    | <a href="${deleteFav}"
         onclick="if (!(confirm('Are you sure you want to delete this favorite?'))) return false">Delete</a><br/><br/>
	 </c:forEach>
    </div>
   </div>
  </div>

 </div>
</body>
</html>