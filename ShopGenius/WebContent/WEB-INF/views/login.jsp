<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h2>${appName} - Login Form</h2>

<c:if test="${not empty errorMessage}">
	<div style="color: red; font-weight: bold; margin: 30px 0px;">${errorMessage}</div>
</c:if>

<form name='login' action="<c:url value="/login" />" method='POST'>
	<table>
		<tr>
			<td>Email:</td>
			<td><input type='text' name='email' value=''></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><input type='password' name='password' /></td>
		</tr>
		<tr>
			<td colspan='2'><input name="submit" type="submit"
				value="submit" /></td>
		</tr>
	</table>
</form>
