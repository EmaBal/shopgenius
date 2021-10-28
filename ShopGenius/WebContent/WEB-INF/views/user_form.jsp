<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration</title>
<link href="<c:url value="/WEB-INF/css/bootstrap.min.css" />"
 rel="stylesheet">
<script src="<c:url value="/WEB-INF/js/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/WEB-INF/js/bootstrap.min.js" />"></script>

</head>
<body>
 <div class="container">
  <div class="col-md-offset-2 col-md-7">
   <h2 class="text-center">Register</h2>
   <div class="panel panel-info">
    <div class="panel-body">
     <form:form action="saveUser" cssClass="form-horizontal" method="post" modelAttribute="user">

      <form:hidden path="id" />

      <div class="form-group">
       <label for="firstname" class="col-md-3 control-label">First Name</label>
       <div class="col-md-9">
        <form:input path="firstName" cssClass="form-control" />  <form:errors path="firstName" cssStyle="color: #ff0000;"/>
       </div>
      </div>
      <div class="form-group">
       <label for="lastname" class="col-md-3 control-label">Last Name</label>
       <div class="col-md-9">
        <form:input path="lastName" cssClass="form-control" />  <form:errors path="lastName" cssStyle="color: #ff0000;"/>
       </div>
      </div>

      <div class="form-group">
       <label for="email" class="col-md-3 control-label">Email</label>
       <div class="col-md-9">
        <form:input path="email" cssClass="form-control" />  <form:errors path="email" cssStyle="color: #ff0000;"/>
       </div>
      </div>
      
       <div class="form-group">
       <label for="password" class="col-md-3 control-label">Password</label>
       <div class="col-md-9">
        <form:input type="password" path="password" cssClass="form-control" />  <form:errors path="password" cssStyle="color: #ff0000;"/>
       </div>
      </div>
      <c:if test="${role eq 'admin'}">
      	<div>
		  <input type="checkbox" id="makeAdmin" name="makeAdmin">
		  <label for="scales">Admin</label>
		</div>
      </c:if>
		<br/>
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