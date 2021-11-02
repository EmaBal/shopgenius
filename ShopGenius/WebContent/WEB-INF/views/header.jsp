<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.6.1/font/bootstrap-icons.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="/ShopGenius/"><img src="<c:url value="/resources/img/shopgenius_black.png"/>" width="32" height="32"/>ShopGenius</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <c:choose>
        <c:when test="${role eq 'admin'}">
        <li class="nav-item">
          <a class="nav-link" href="/ShopGenius/user/list">Manage Users</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/ShopGenius/product/list">Manage Products</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/ShopGenius/favorites">Favorites</a>
        </li>
        </c:when>
        <c:when test="${role eq 'user'}">
        <li class="nav-item">
          <a class="nav-link" href="/ShopGenius/favorites">Favorites</a>
        </li>
        </c:when>
        </c:choose>
      </ul>
      <c:choose>
      	<c:when test="${role eq 'anonymous'}">
		    <div class="btn-group">
			  <button type="button" class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" data-bs-display="static" aria-expanded="false">
				<i class="bi bi-person-circle"> Login/Register</i>
			  </button>
			  <ul class="dropdown-menu dropdown-menu-lg-end">
			    <li><a class="dropdown-item" href="/ShopGenius/login">Login</a></li>
			    <li><a class="dropdown-item" href="/ShopGenius/user/showForm">Register</a></li>
			  </ul>
			</div>
		</c:when>
		<c:otherwise>
			<div class="btn-group">
			  <button type="button" class="btn btn-primary dropdown-toggle" data-bs-toggle="dropdown" data-bs-display="static" aria-expanded="false">
				<i class="bi bi-person-circle text-capitalize"> ${current_firstName} ${current_lastName}</i>
			  </button>
			  <ul class="dropdown-menu dropdown-menu-lg-end">
			  	<li><a class="dropdown-item" href="/ShopGenius/user/details">Profile</a></li>
			    <li><a class="dropdown-item" href="/ShopGenius/logout">Logout</a></li>
			  </ul>
			</div>
		</c:otherwise>
	</c:choose>
    </div>
  </div>
</nav>