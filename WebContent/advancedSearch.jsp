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
<c:choose> 
	<c:when test="${user.getUsername()==null || user.getUsername()=='NULL'}"> <%@ include file="header.jsp"%> </c:when>    
	<c:otherwise> <%@ include file="HeaderUser.html"%> </c:otherwise>
</c:choose>

<div class="middleSection">    
	<div class= "middleSect">
		<form action='ControllerServlet?operation=advancedSearch' class='registerForm' method='POST'>
			Publication Type: 
			<select name="type">
				<option></option>
				<option value="book/collection">Books/Collections</option>
				<option value="journal">Journal</option>
				<option value="conference">Conference</option>
				<option value="editorship">Editorship</option>
			</select>
			<br>
			<br>
			Title: <input type="text" name="title">
			<br>
			Author: <input type="text" name="author">
			<br>
			Publication Year: <input type="text" name="year">
			<br>
			ISBN: <input type="text" name="isbn">
			<br>
			Price Range:
			<br>
			Min: <input type="text" name="minPrice">
			<br>
			Max: <input type="text" name="maxPrice">
			<br>
			<input type="submit" value="Search">
		</form>
	</div>
</div>

<%@ include file="footer.jsp"%>
</body>
</html>
