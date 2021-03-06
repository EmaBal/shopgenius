<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
 <div class="container">
  <div class="col-md-offset-1 col-md-10"><br/>
   <h2><i class="bi bi-people"></i> Manage Users</h2>
   <hr />

   <input type="button" value="Add User" onclick="window.location.href='showForm'; return false;" class="btn btn-primary" />
    <br/><br/>
     <table id="usersTable" class="table table-striped table-bordered text-center">
      <tr>
       <th>First name</th>
       <th>Last name</th>
       <th>Email</th>
       <th>Roles</th>
       <th colspan="2" class="mx-auto">Action</th>
      </tr>

      <c:forEach var="tempUser" items="${users}">

       <c:url var="updateLink" value="/user/updateForm">
        <c:param name="userId" value="${tempUser.id}" />
       </c:url>

       <c:url var="deleteLink" value="/user/delete">
        <c:param name="userId" value="${tempUser.id}" />
       </c:url>

       <tr>
        <td>${tempUser.firstName}</td>
        <td>${tempUser.lastName}</td>
        <td>${tempUser.email}</td>
        <td>
        <c:forEach var="role" items="${tempUser.getRoles()}">
        ${role.getName()} 
		</c:forEach>
		</td>
        <td>
         <a href="${updateLink}" style="text-decoration: none">Update</a>
        </td>
        <td>
        <c:choose>
        <c:when test="${currentUserEmail eq tempUser.email}">
        	<p style="color:grey; display:inline">Delete</p>
        </c:when>
        <c:otherwise>
			 <a href="${deleteLink}" style="text-decoration: none" onclick="if (!(confirm('Are you sure you want to delete this user?'))) return false">Delete</a>        
        </c:otherwise>
        </c:choose>
        </td>

       </tr>

      </c:forEach>

     </table>

    </div>
   </div>
   <script>
</script>
</body>
</html>