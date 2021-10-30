<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<body>
 <div class="container">
  <div class="col-md-offset-1 col-md-10"><br/>
   <h2>Manage Users</h2>
   <hr />

   <input type="button" value="Add User" onclick="window.location.href='showForm'; return false;" class="btn btn-primary" />
    <br/><br/>
     <table id="sortTable" class="table table-striped table-bordered text-center">
      <tr>
       <th>First name</th>
       <th>Last name</th>
       <th>Email</th>
       <th>Roles</th>
       <th colspan="2" class="mx-auto">Action</th>
      </tr>

      <!-- loop over and print our customers -->
      <c:forEach var="tempUser" items="${users}">

       <!-- construct an "update" link with customer id -->
       <c:url var="updateLink" value="/user/updateForm">
        <c:param name="userId" value="${tempUser.id}" />
       </c:url>

       <!-- construct an "delete" link with customer id -->
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
         <a href="${deleteLink}" style="text-decoration: none" onclick="if (!(confirm('Are you sure you want to delete this user?'))) return false">Delete</a>
        </td>

       </tr>

      </c:forEach>

     </table>

    </div>
   </div>
   <script>
$('#sortTable').DataTable();
</script>
</body>
</html>