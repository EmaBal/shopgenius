<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
 <div class="container">
  <div class="col-md-offset-2 col-md-7"><br/>
   <h2>Register</h2><br/>
   <div class="panel panel-info">
    <div class="panel-body">
     <form:form action="saveUser" method="post" modelAttribute="user">

      <form:hidden path="id" />

      <div class="mb-3">
       <label for="firstname" class="col-md-3 control-label">First Name</label>
       <div class="col-md-9">
        <form:input path="firstName" cssClass="form-control" />  <form:errors path="firstName" cssStyle="color: #ff0000;"/>
       </div>
      </div>
      <div class="mb-3">
       <label for="lastname" class="col-md-3 control-label">Last Name</label>
       <div class="col-md-9">
        <form:input path="lastName" cssClass="form-control" />  <form:errors path="lastName" cssStyle="color: #ff0000;"/>
       </div>
      </div>

      <div class="mb-3">
       <label for="email" class="col-md-3 control-label">Email</label>
       <div class="col-md-9">
        <form:input path="email" cssClass="form-control" />  <form:errors path="email" cssStyle="color: #ff0000;"/>
       </div>
      </div>
      
       <div class="mb-3">
       <label for="password" class="col-md-3 control-label">Password</label>
       <div class="col-md-9">
        <form:input type="password" path="password" cssClass="form-control" />  <form:errors path="password" cssStyle="color: #ff0000;"/>
       </div>
      </div>
      <c:if test="${role eq 'admin'}">
      	<div class="mb-3 form-check">
		  <input type="checkbox" class="form-check-input" id="makeAdmin" name="makeAdmin">
		  <label class="form-check-label" for="makeAdmin">Admin</label>
		</div>
      </c:if>
		<br/>
         <button type="submit" class="btn btn-primary">Submit</button>

     </form:form>
    </div>
   </div>
  </div>
 </div>
</body>
</html>