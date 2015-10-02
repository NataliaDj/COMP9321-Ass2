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
		<c:choose>
			<c:when test="${empty publications}">
				<h3>Sorry, no matching datasets found!</h3>
			</c:when>
			<c:otherwise>
				<h2 align="center">Search Results</h2>
			   		<table style="width:100%" cellpadding="10" class="results">
			   		<tr>
			   			<th align="left">
			   				Title
			   			</th>
			   			<th align="left">
			   				Author(s)
			   			</th>
			   		</tr>
			   		<c:forEach var="i" items="${publications}">
			   		<tr>
			   			<td>
<<<<<<< HEAD
			   				<a href="/viewPublication.jsp">${i.title}</a>
=======
			   				<a href="ControllerServlet?operation=advancedSearch&title=${i.title}&type=${i.pubType}&link=true">${i.title}</a>
>>>>>>> 01fc8da187e4302818ec9186171e91fd5492507a
			   			</td>
			   			<td>
			   				<c:forEach var="j" items="${i.author}" varStatus="loop">
			   					${j}
			   					${!loop.last ? ',' : ''}
			   				</c:forEach>
			   			</td>
			   		</tr>			
			   		</c:forEach>
			   		</table>
			</c:otherwise>
	</c:choose>
	</div>
</div>
</body>
<%@ include file="footer.jsp"%>
</html>