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
Benvenuto/a, ${username}<br/>
<c:if test="${role eq 'admin'}">
	<input name="addProduct" type="button" value="Aggiungi Prodotto" onclick="location.href='/addProduct';"/>
</c:if><br/>
<form action="searchProduct">
  <label for="fname">Cerca prodotto:</label>
  <input type="text" id="fname" name="productName"><br>
  <input type="submit" value="Cerca" onclick="location.href='/product';">
</form>
<c:if test="${not empty errorMessage}">
	<div style="color: red; font-weight: bold; margin: 30px 0px;">${errorMessage}</div>
</c:if>
</body>
</html>