<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><c:out value="${userDTO.username}" /></title>
<link href="frontpage.css" rel="stylesheet" type="text/css">
<link href="resultsTable.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@ include file="/header.jsp"%>
	<div class="middleSection">
		<div class="middleSect">
			<center>
				<h2>Purchased</h2>
			</center>
			<table style="width: 100%" cellpadding="10" class="results">
				<tr>
					<th align="left">Title</th>
					<th align="left">Timestamp</th>
					<th align="left">Price</th>
					<th align="left">Seller</th>
				</tr>
				<c:forEach var="current" items="${cart}">
					<c:if test="${not empty current.purchased}">
						<tr>
							<td><a href="ControllerServlet?operation=viewPublication&id=${current.publication.id}">
							<c:out value="${current.publication.title}" />
							</a></td>		
							<td><c:out value="${current.purchased}" /></td>			
							<td><c:out value="${current.publication.price}" /></td>			
							<td><c:out value="${current.publication.seller}" /></td>							
						</tr>
					</c:if>
				</c:forEach>
			</table>
			
			<center>
				<h2>Removed</h2>
			</center>
			<table style="width: 100%" cellpadding="10" class="results">
				<tr>
					<th align="left">Title</th>
					<th align="left">Added</th>
					<th align="left">Removed</th>
					<th align="left">Price</th>
					<th align="left">Seller</th>
				</tr>
				<c:forEach var="current" items="${cart}">
					<c:if test="${not empty current.removed}">
						<tr>
							<td><a href="ControllerServlet?operation=viewPublication&id=${current.publication.id}">
							<c:out value="${current.publication.title}" />
							</a></td>			
							<td><c:out value="${current.added}" /></td>
							<td><c:out value="${current.removed}" /></td>			
							<td><c:out value="${current.publication.price}" /></td>			
							<td><c:out value="${current.publication.seller}" /></td>							
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>
	</div>
	<%@ include file="/footer.jsp"%>
</body>
</html>