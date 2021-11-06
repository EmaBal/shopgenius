<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
function capitalizeFirstLetter(string) {
	  return string.charAt(0).toUpperCase() + string.slice(1);
	}
capitalizeFirstLetter(${tempProduct.name});
capitalizeFirstLetter(${tempProduct.productType.typeName});
</script>
<body>
 <div class="container">
  <div class="col-md-offset-1 col-md-10"><br/>
   <h2><i class="bi bi-cart"></i> Manage Products</h2>
   <hr />

   <input type="button" value="Add Product" onclick="window.location.href='add'; return false;" class="btn btn-primary" />
    <br/><br/>
     <table id="productsTable" class="table table-striped table-bordered text-center">
      <tr>
       <th>Name</th>
       <th>Price</th>
       <th>Available quantity</th>
       <th>Product type/Department</th>
       <th>Location detail</th>
       <th colspan="2" class="mx-auto">Action</th>
      </tr>

      <c:forEach var="tempProduct" items="${products}">

       <c:url var="updateLink" value="/product/update">
        <c:param name="productId" value="${tempProduct.id}" />
       </c:url>

       <c:url var="deleteLink" value="/product/delete">
        <c:param name="productId" value="${tempProduct.id}" />
       </c:url>
       
	    <c:url var="viewDetails" value="/product">
	    	<c:param name="productName" value="${tempProduct.name}" />
	    </c:url>

       <tr>
        <td><a href="${viewDetails}" style="text-decoration: none">${tempProduct.name}</a></td>
        <td>${tempProduct.price} &#8364;</td>
        <td>${tempProduct.quantity}</td>
        <td>${tempProduct.productType.typeName}</td>
        <td>${tempProduct.locationDetail}</td>
        
        <td><a href="${updateLink}" style="text-decoration: none">Update</a></td>
        <td><a href="${deleteLink}" style="text-decoration: none" onclick="if (!(confirm('Are you sure you want to delete this product?'))) return false">Delete</a></td>
        
       </tr>

      </c:forEach>

     </table>
    </div>
   </div>
</body>
</html>