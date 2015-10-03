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
		<form action='ControllerServlet?operation=register' class='registerForm' method='POST'>
	
			<c:choose>
				<c:when test="${user.getUsername()==null || user.getUsername()=='NULL'}">
					<h2>Register</h2>
		    		<p>Please fill in all information</p>   
		    		
		    		<p>Account type: <input type=checkbox name="buyer" checked>Buyer
  					<input type="checkbox" name="seller">Seller</p>
				</c:when>    
				<c:otherwise>

					<h2>Edit profile profile</h2>
 
				</c:otherwise>
			</c:choose>
			${info}
			<c:choose>
			    <c:when test="${user.getUsername()==null || user.getUsername()=='NULL'}">
			        Username <input type="text" name="username">
			    </c:when>    
			    <c:otherwise>
			       <input type="hidden" name="username" value=${user.getUsername()}>
			    </c:otherwise>
			</c:choose>
			Password <input type="password" name="password">
			Email: <input type="text" name="email"  value=${user.getEmail()}>
			<br>
			<h3>Personal information</h3>
		    First name <input type="text" name="firstname" value=${user.getFirstName()}>
		   	Last name <input type="text" name="lastname"  value=${user.getLastName()}>
		  	Birth year: <input type="text" name="birth_year"  value=${user.getBirthYear()}>
		   	<h3>Address</h3>
		   	Address 1 <input type="text" name="address_one">
		   	Address 2 <input type="text" name="address_two">
			City <input type="text" name="city">
			Postal code <input type="text" name="postal_code">
			State <input type="text" name="state">
			Country <input type="text" name="country">
			<h3>Payment</h3>
			
	        
	        
	        <c:choose>
			    <c:when test="${user.getUsername()=='NULL' || user.getUsername()==null}">
			        Credit card: <input type="text" name="credit_card" value="" placeholder="Only if buyer">
	        		Paypal: <input type="text" name="paypal" value="" placeholder="Only if seller">
			        
			        <input type="hidden" name="action" value="registering"/>
					<input type='submit' value='Submit'/>
			    </c:when>    
			    <c:otherwise>
			    	<c:choose>
					    <c:when test="${user.hasBuyerDTO()==true}">
					       Credit card: <input type="text" name="credit_card" value=${user.getBuyerDTO().getCreditCard()}>
					    </c:when>  
					</c:choose><c:choose>
					    <c:when test="${user.hasSellerDTO()==true}">
					       Paypal: <input type="text" name="paypal" value=${user.getSellerDTO().getPaypal()}>
					    </c:when>  
			    	</c:choose>
		
			       <input type="hidden" name="action" value="updating_user"/>
					<input type='submit' value='Update'/>
			    </c:otherwise>
			</c:choose>
		    
		</form>
	</div>
</div>
<%@ include file="footer.jsp"%>
</body>
</html>