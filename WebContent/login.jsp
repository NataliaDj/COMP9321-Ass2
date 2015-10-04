<%-- This is the welcome page and does absolutely nothing other than welcome the user--%>
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
</head>

<body>

<%@ include file="header.jsp"%>

<!-- <form action='results.jsp'>-->
<div class="middleSection">    
	<div class= "middleSect">
	    <h2>Login</h2>
		<p><b>${error}</b></p>
		<form action='ControllerServlet?operation=login' class='registerForm' method='POST'>
			Username <input type="text"name="username" maxlength="32" pattern="[A-Za-z0-9]{4,}" 
						title="At least 4 or more letters and/or numbers" required>
			Password <input type="password" name="password" maxlength="32" pattern="[A-Za-z0-9]{8,}" 
						title="At least 8 or more letters and/or numbers" required>
	        
	    <!--<p><input type='submit' value='Search'></form></p>-->
		    <input type="hidden" name="action" value="loggingin"/>
			<input type='submit' value='Submit'/>
		</form>
	</div>
</div>
<%@ include file="footer.jsp"%>
</body>
</html>