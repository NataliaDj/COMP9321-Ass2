<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="edu.unsw.comp9321.*"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>New Listing Success</title>
	<link href="frontpage.css" rel="stylesheet" type="text/css">
</head>
<body>

<%@ include file="header.jsp"%>

<div class="middleSection">    
	<div class= "middleSect">
		<c:choose>
			<c:when test="${not empty message }">
				<h2>${message }</h2>
			</c:when>
			<c:otherwise>
				<h2>Success! Your publication has successfully been listed</h2>
				<br/>
				<h3>You can now find your book listed from the main page</h3>
			</c:otherwise>
		</c:choose>
	</div>
</div>

<%@ include file="footer.jsp"%>
</body>
</html>