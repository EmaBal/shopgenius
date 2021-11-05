<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="https://code.jquery.com/ui/1.13.0/themes/base/jquery-ui.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script>
<script type="text/javascript">
	$(function() {
		$("#product_name").autocomplete({
			source: "product/autocomplete",
			minLength: 1
		});
	});
</script>
<body>
<%-- <div style="background-image: url('${pageContext.request.contextPath}/resources/img/background_cover3.jpg'); height: 100vh; background-size: contain; background-repeat: no-repeat;"> --%>
<img class="img-fluid" src="${pageContext.request.contextPath}/resources/img/background_cover5.jpg"/>
<div class="container">
<div class="row">
<h2 class="mt-5 text-center"><i class="bi bi-search"></i> Search Product</h2>
</div><br/>
<div class="row">
<form class="d-flex" action="product/search" method = "POST">
        <input class="form-control me-2" type="text"
        placeholder="Search" aria-label="Search" name="productName" id="product_name">
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