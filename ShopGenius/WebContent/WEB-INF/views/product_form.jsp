<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib  uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add product</title>
<link href="<c:url value="/WEB-INF/css/bootstrap.min.css" />"
 rel="stylesheet">
<script src="<c:url value="/WEB-INF/js/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/WEB-INF/js/bootstrap.min.js" />"></script>

</head>
<body>
 <div class="container">
  <div class="col-md-offset-2 col-md-7">
   
   <div class="panel panel-info">
    <div class="panel-body">
     <form:form action="save" cssClass="form-horizontal"
      method="post" modelAttribute="product">
      <c:choose>
	      <c:when test="${empty product.name}">
	      	<h2 class="text-center">Add product</h2>
	      </c:when>
	      <c:otherwise>
	      	<h2 class="text-center">Update product</h2>
	      </c:otherwise>
      </c:choose>
      <form:hidden path="id" />

      <div class="form-group">
       <label for="name" class="col-md-3 control-label">Product Name</label>
       <div class="col-md-9">
        <form:input path="name" cssClass="form-control" />
       </div>
      </div>
      <div class="form-group">
       <label for="price" class="col-md-3 control-label">Price</label>
       <div class="col-md-9">
        <form:input path="price" cssClass="form-control" />
       </div>
      </div>

      <div class="form-group">
       <label for="quantity" class="col-md-3 control-label">Quantity</label>
       <div class="col-md-9">
        <form:input type="number" path="quantity" cssClass="form-control" />
       </div>
      </div>
      
        <div class="form-group">
       <label for="locationDetail" class="col-md-3 control-label">Location detail</label>
       <div class="col-md-9">
        <form:input path="locationDetail" cssClass="form-control" />
       </div>
      </div>
      
      <div class="form-group">
	  	<label for="type" class="col-md-3 control-label">Product Type</label>
	    <div class="col-md-9">
	        <select name="typeName" id="typeName">
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
      </div>

      <div class="form-group">
       <!-- Button -->
       <div class="col-md-offset-3 col-md-9">
        <form:button cssClass="btn btn-primary">Submit</form:button>
       </div>
      </div>

     </form:form>

    </div>
   </div>
  </div>
 </div>
</body>
</html>