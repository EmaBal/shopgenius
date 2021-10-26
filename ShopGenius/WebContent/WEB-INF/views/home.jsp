<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Homepage</title>
</head>
<body>
<c:choose>
	<c:when test="${role eq 'admin'}">
		Benvenuto/a, ${username}<br/>
		<input name="addProduct" type="button" value="Aggiungi Prodotto" onclick="location.href='addProduct';"/><br/>
		<input name="favorites" type="button" value="Visualizza Preferiti" onclick="location.href='favorites';"/><br/>
		<input name="listUsers" type="button" value="Visualizza Utenti Registrati" onclick="location.href='user/list';"/><br/>
		<input name="logout" type="button" value="Logout" onclick="location.href='logout';"/><br/>
	</c:when>
	<c:when test="${role eq 'user'}">
		Benvenuto/a, ${username}<br/>
		<input name="favorites" type="button" value="Visualizza preferiti" onclick="location.href='favorites';"/><br/>
		<input name="logout" type="button" value="Logout" onclick="location.href='logout';"/><br/>
	</c:when>
	<c:otherwise>
		<input name="login" type="button" value="Login" onclick="location.href='login';"/><br/>
		<input name="register" type="button" value="Registrati" onclick="location.href='user/showForm';"/><br/>
	</c:otherwise>
</c:choose>
<form action="searchProduct" method = POST>
  <label for="fname">Cerca prodotto:</label>
  <input type="text" id="fname" name="productName"><br>
  <!-- <input type="submit" value="Cerca" onclick="location.href='/product';"> -->
  <input type="submit" value="Cerca">
</form>
<%-- <c:if test="${not empty error}"> --%>
	<div style="color: red; font-weight: bold; margin: 30px 0px;">${error}</div>
<%-- </c:if> --%>
</body>
</html>