<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
 <div class="container">
  <div class="col-md-offset-2 col-md-7"><br/>
   <c:choose>
    <c:when test="${empty user.email}">
    	<h2>Register</h2><br/>
    </c:when>
    <c:otherwise>
    	<h2>Update</h2><br/>
    </c:otherwise>
    </c:choose>
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
      <input type="hidden" name="updateRole" value="${update_role}"/>
      <div class="mb-3">
    <c:choose>
	    <c:when test="${empty update_role}">
	    	<label for="role" class="form-label">Role</label>
	    </c:when>
	    <c:otherwise>
	    	<label for="role" class="form-label">Role <i>(current: ${update_role})</i></label>
	    </c:otherwise>
    </c:choose>
     <div class="col-md-9">
    <select class="form-select text-capitalize" name="roleName" id="roleName">
				<c:forEach var="r" items="${roleNamesList}">
				<c:choose>
	      			<c:when test="${not empty user.email}">
						<c:choose>
							<c:when test="${r eq update_role}">
								<option selected value="${r}"> ${r} </option>
							</c:when>
							<c:otherwise>
								<option value="${r}"> ${r} </option>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<option value="${r}"> ${r} </option>
					</c:otherwise>
				</c:choose>
	        	</c:forEach>
			</select>
			</div>
  </div>
      </c:if>
      <c:if test="${not empty error}">
			<div class="py-3 col-md-9">
				<div class="alert alert-danger" role="alert">
				  ${error}
				</div>
			</div>
		</c:if>
		<br/>
		<input type="hidden" name="oldUserEmail" value="${user.email}"/>
         <button type="submit" class="btn btn-primary">Submit</button>

     </form:form>
    </div>
   </div>
</body>
</html>