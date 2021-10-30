<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body>
<div class="container my-5">
<div class="row">
<h3>Search Product:</h3>
<br/><br/></div>
<div class="row">
<form class="d-flex" action="product/search" method = "POST">
        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search" name="productName">
        <button class="btn btn-outline-primary" type="submit">Search</button>
      </form>
</div>
<c:if test="${not empty error}">
	<div class="py-3">
		<div class="alert alert-danger" role="alert">
		  ${error}
		</div>
	</div>
</c:if>
</div>

</body>
</html>