<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<body>
 <div class="container">
  <div class="col-md-offset-1 col-md-10"><br/>
   <h2><i class="bi bi-star-fill"></i> Favorites List</h2>
	<hr />
	<c:choose>
	  <c:when test="${empty favorites}">
		  No favorites found &#128577;
	  </c:when>
	  <c:otherwise>
	<table class="table table-borderless">
	  <tbody>
	    <c:forEach var="favorite" items="${favorites}">
		    <c:url var="viewDetails" value="/product">
		    	<c:param name="productName" value="${favorite.name}" />
		    </c:url>
		    <c:url var="deleteFav" value="/favorites/delete">
		    	<c:param name="productId" value="${favorite.id}" />
		    </c:url>
		    <tr>
<%-- 		    	<td class="align-middle">
		 	    	<p class="fs-5"><a href="${deleteFav}" onclick="if (!(confirm('Are you sure you want to delete this favorite?'))) return false"><i class="bi bi-x-circle" style="color:red"></i></a></p>
		 	    </td> --%>
			    <td class="align-middle mb-0">
			    	<p class="fs-5 mb-0"><a href="${deleteFav}" onclick="if (!(confirm('Are you sure you want to delete this favorite?'))) return false"><i class="bi bi-x-circle" style="color:red"></i></a><a href="${viewDetails}" class="text-capitalize" style="text-decoration: none; color:black">&nbsp;&nbsp;&nbsp;${favorite.name}</a></p>
			    </td>
		 	</tr>
		 </c:forEach>
	  </tbody>
	</table>
	</c:otherwise>
	</c:choose>
  </div>
 </div>
 
</body>
</html>