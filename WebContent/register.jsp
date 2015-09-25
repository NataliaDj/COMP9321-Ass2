<%-- This is the welcome page and does absolutely nothing other than welcome the user--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!-- <%@ page import="edu.unsw.comp9321.*"%> -->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Welcome!</title>
	<link href="frontpage.css" rel="stylesheet" type="text/css">
</head>

<body>
<%@ include file="Header.html"%>

<!-- <form action='results.jsp'>-->
<div class="middleSection">    
	<div class= "middleSect">
	    <h2>Register</h2>
	    <p>Please fill in all information</p>
	
		<form action='dispatcher?operation=register' class='registerForm' method='POST'>
			Username <input type="text"name="username">
			Password <input type="password" name="password">
			Email: <input type="text" name="email">
			<br>
			<h3>Personal information</h3>
		    First name <input type="text" name="firstname">
		   	Last name <input type="text" name="lastname">
		  	Birth year: <input type="text" name="birth_year">
		   	<h3>Address</h3>
		   	Address 1 <input type="text" name="address_one">
		   	Address 2 <input type="text" name="address_two">
			City <input type="text" name="city">
			Postal code <input type="text" name="postal_code">
			State <input type="text" name="state">
			Country <input type="text" name="country">
			<h3>Payment</h3>
		    Credit card number <input type="text" name="creditcard">
	        
	    <!--<p><input type='submit' value='Search'></form></p>-->
		    <input type="hidden" name="action" value="registering"/>
			<input type='submit' value='Submit'/>
		</form>
	</div>
</div>
<%@ include file="footer.jsp"%>
</body>
</html>