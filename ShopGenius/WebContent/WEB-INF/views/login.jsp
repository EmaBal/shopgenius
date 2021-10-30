<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container">
  <div class="col-md-offset-2 col-md-7"><br/>
  <h2>Login</h2><br/>
	<form name='login' action="<c:url value="/login" />" method='POST'>
	  <div class="mb-3">
	    <label for="email" class="form-label">Email address</label>
	    <input type="email" name="email" class="form-control" id="email">
	  </div>
	  <div class="mb-3">
	    <label for="password" class="form-label">Password</label>
	    <input type="password" name="password" class="form-control" id="password">
	  </div>
	  <button type="submit" class="btn btn-primary">Submit</button>
	</form>
	<c:if test="${not empty errorMessage}">
			<div class="py-3">
				<div class="alert alert-danger" role="alert">
				  ${errorMessage}
				</div>
		</div>
	</c:if>
</div>
</div>