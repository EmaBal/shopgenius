<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<body>
 <div class="container">
  <div><br/>
   <h2 class="text-capitalize">User Details</h2>
	   
   <hr />
    
     <table class="table table-striped table-bordered">
       <tr>
       <td><b>First name</b></td>
       <td class="text-capitalize">${user.firstName}</td>
      </tr>
		<tr>
       <td><b>Last name</b></td>
       <td class="text-capitalize">${user.lastName}</td>
      </tr>
       <tr>
       <td><b>Email address</b></td>
       <td>${user.email}</td>
      </tr>
      <tr>
       <td><b>Password</b></td>
       <td>${user.password}</td>
      </tr>
     </table>
     
    </div>
   </div>
</body>
</html>