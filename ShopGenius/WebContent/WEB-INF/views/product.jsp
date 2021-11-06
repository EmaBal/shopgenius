<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
function capitalizeFirstLetter(string) {
	  return string.charAt(0).toUpperCase() + string.slice(1);
	}
capitalizeFirstLetter(${product.name});
capitalizeFirstLetter(${product.productType.typeName});
</script>
<body>
 <div class="container">
  <div><br/>
   <h2>${product.name}</h2>
	   <c:if test="${role ne 'anonymous'}">
		     <c:choose>
				<c:when test="${isProductFav eq true}">
					<form action="favorites/delete">
					<input type="hidden" name="productId" value="${product.id}"/>
						<button type="submit" class="btn btn-default">
							<i class="bi bi-star-fill"> Remove from Favorites</i>
						</button>
						<br/>
					</form>
				</c:when>
				<c:otherwise>
				<form action="favorites/add">
					<input type="hidden" name="productName" value="${product.name}" />
					<button type="submit" class="btn btn-default">
					    <i class="bi bi-star" class="img-fluid"> Add to Favorites</i>
					</button>
				</form>
			</c:otherwise>
			</c:choose>
		</c:if>
   <hr />
    
     <table class="table table-striped table-bordered">
      <tr>
       <td><b>Price</b></td>
       <td>${product.price} &#8364;</td>
      </tr>
      <tr>
      <c:choose>
      <c:when test="${product.quantity == 0}">
	   <td><b>Available quantity</b></td>
       <td style="color:red">Not available</td>
      </c:when>
      <c:otherwise>
       <td><b>Available quantity</b></td>
       <td>${product.quantity}</td>
      </c:otherwise>
      </c:choose>
      </tr>
      <tr>
       <td><b>Product type</b></td>
       <td>${product.productType.typeName}</td>
      </tr>
      <tr>
       <td><b>Location detail</b></td>
       <td>${product.locationDetail}</td>
      </tr>
     </table>

    </div>
   </div>
</body>
</html>