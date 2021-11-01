<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib  uri="http://www.springframework.org/tags" prefix="spring"%>

<body>
 <div class="container">
  <div class="col-md-offset-2 col-md-7">
	<br/>
      <c:choose>
	      <c:when test="${empty product.name}">
	      	<h2>Add Product</h2>
	      </c:when>
	      <c:otherwise>
	      	<h2>Update Product</h2>
	      </c:otherwise>
      </c:choose>
      
<form:form action="save" method="post" modelAttribute="product">
<form:hidden path="id" />
  <div class="mb-3">
    <label for="name" class="form-label">Product name</label>
    <form:input type="name" path="name" class="form-control" id="name" /> <form:errors path="name" cssStyle="color: #ff0000;"/>
  </div>
  <div class="mb-3">
    <label for="price" class="form-label">Price (&#8364;)</label>
    <form:input type="number" path="price" class="form-control" id="price"  step='0.01' placeholder='0.00' /> <form:errors path="price" cssStyle="color: #ff0000;"/>
  </div>
  <div class="mb-3">
    <label for="quantity" class="form-label">Available quantity</label>
    <form:input type="number" path="quantity" class="form-control" id="quantity" /> <form:errors path="quantity" cssStyle="color: #ff0000;"/>
  </div>
  <div class="mb-3">
    <label for="locationDetail" class="form-label">Location detail</label>
    <form:input type="locationDetail" path="locationDetail" class="form-control" id="locationDetail" /> <form:errors path="locationDetail" cssStyle="color: #ff0000;"/>
  </div>
  <div class="mb-3">
    <label for="type" class="form-label">Product type</label>
    <select class="form-select text-capitalize" name="typeName" id="typeName">
				<c:forEach var="type" items="${productTypes}">
				<c:choose>
	      			<c:when test="${not empty product.productType.typeName}">
						<c:choose>
							<c:when test="${type.typeName eq product.productType.typeName}">
								<option selected value="${type.typeName}"> ${type.typeName} </option>
							</c:when>
							<c:otherwise>
								<option value="${type.typeName}"> ${type.typeName} </option>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<option value="${type.typeName}"> ${type.typeName} </option>
					</c:otherwise>
				</c:choose>
	        	</c:forEach>
			</select>
  </div>
  <button type="submit" class="btn btn-primary">Submit</button>
</form:form>
    </div>
   </div>
</body>
</html>