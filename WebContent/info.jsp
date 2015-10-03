<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="edu.unsw.comp9321.*"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Welcome!</title>
	<link href="frontpage.css" rel="stylesheet" type="text/css">
	<link href="resultsTable.css" rel="stylesheet" type="text/css">
</head>

<body>
<%@ include file="header.jsp"%>

<div class="middleSection">
	<div class= "middleSect">
	<c:if test="${not empty publication.picture}">
		<br>
		<center><img src="${publication.picture}" height="600" width="370"></center>
		<br>
		<br>
	</c:if>
	<table style="width:100%" cellpadding="10" class="results">
   	<tr>
   		<td>
   			Title
   		</td>
   		<td>
   			${publication.title}
   		</td>
   	</tr>
   	<tr>
		<td>
			Price
		</td>
		<td>
			AUD $${publication.price}
   		</td>
   	</tr>
   	<tr>
		<td>
			Author(s)
		</td>
		<td>
			<c:forEach var="i" items="${publication.author}" varStatus="loop">
   				${i}
   				${!loop.last ? ',' : ''}
   			</c:forEach>
   		</td>
   	</tr>
   	<tr>
   		<td>
   			Publication Type
   		</td>
   		<td>
   			${publication.pubType}
   		</td>
   	</tr>
   	<tr>
		<td>
			Publication Year
		</td>
		<td>
			${publication.pubYear}
   		</td>
   	</tr>
   	<tr>
		<td>
			ISBN
		</td>
		<td>
			${publication.isbn}
   		</td>
   	</tr>
  	</table>
  	<c:if test="${adminAccount == 'yes'}">
  		<a href="ControllerServlet?operation=removePublication&id=${publication.id}">Remove Publication</a>
  	</c:if>
	</div>
		<br>
		<center>
		<form action='ControllerServlet?operation=cart' class='registerForm' method='POST'>
  			<button type="submit">Add to Cart</button>
  		</form>
  		</center>
</div>
</body>
<%@ include file="footer.jsp"%>
</html>