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
		<form action='ControllerServlet?operation=register' class='registerForm' name="registration" method='POST'>
	
			<c:choose>
				<c:when test="${user.getUsername()==null || user.getUsername()=='NULL'}">
					<h2>Register</h2>
		    		<p>Please fill in all information</p>   
		    		
		    		<p>Account type: <input type=checkbox name="buyer" checked>Buyer
  					<input type="checkbox" name="seller">Seller</p>
				</c:when>    
				<c:otherwise>
					<h2>Edit your profile </h2>
					<p>Here you can edit your profile ${user.getNickname()}</p>
				</c:otherwise>
			</c:choose>
			${info}
			<c:choose>
			    <c:when test="${user.getUsername()==null || user.getUsername()=='NULL'}">
			        Username <input type="text" name="username" maxlength="32" pattern="[A-Za-z0-9]{4,}" title="At least 4 or more letters and/or numbers" required>
			        Password <input type="password" name="password" maxlength="32" pattern="[A-Za-z0-9]{8,}" title="At least 8 or more letters and/or numbers" required>
			    </c:when>    
			    <c:otherwise>
			       <input type="hidden" name="username" value=${user.getUsername()} maxlength="32" >
			       Password <input type="password" name="password" placeholder="Only fill in when changing password" pattern="[A-Za-z0-9]{8,}" title="Only numbers and letters allowed" required>
			    </c:otherwise>
			</c:choose>
			
			Email: <input type="text" name="email"  value="${user.getEmail()}" maxlength="32" pattern="[A-Za-z0-9-_\.]{1,}[\@][A-Za-z0-9-_]{1,}[\.][A-Za-z]{1,0}" title="Please enter a valid email address" required>
			<br>
			<h3>Personal information</h3>
			Nickname <input type="text" name="nickname" value="${user.getNickname()}" maxlength="32" pattern="[A-Za-z0-9-' ]*" title="Use of illegal characters">
		    First name <input type="text" name="firstname" value="${user.getFirstName()}" maxlength="32" pattern="[A-Za-z0-9-' ]*" title="Use of illegal characters">
		   	Last name <input type="text" name="lastname"  value="${user.getLastName()}" maxlength="32" pattern="[A-Za-z0-9-' ]*" title="Use of illegal characters">
		  	Birth year: <input type="text" name="birth_year"  value="${user.getBirthYear()}" maxlength="4" pattern="[0-9]{4}" title="Please enter a 4 digit year">
		   	<h3>Address</h3>
		   	Address 1 <input type="text" name="address_one" value="${user.getAddressOne()}" maxlength="32" pattern="[A-Za-z0-9-' ]*" title="Use of illegal characters">
		   	Address 2 <input type="text" name="address_two" value="${user.getAddressTwo()}" maxlength="32" pattern="[A-Za-z0-9-' ]*" title="Use of illegal characters">
			City <input type="text" name="city" value="${user.getAddressCity()}" maxlength="32" pattern="[A-Za-z-' ]*" title="Use of illegal characters">
			Postal code <input type="text" name="postal_code" value="${user.getAddressPostalCode()}" maxlength="32" pattern="[A-Za-z0-9-' ]*" title="Use of illegal characters">
			State <input type="text" name="state" value="${user.getAddressState()}" maxlength="32" pattern="[A-Za-z-' ]*" title="Use of illegal characters">
			Country <input type="text" name="country" value="${user.getAddressCountry()}" maxlength="32" pattern="[A-Za-z-' ]*" title="Use of illegal characters">
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
					       Credit card: <input type="text" name="credit_card" value="${user.getBuyerDTO().getCreditCard()}">
					    </c:when>  
					</c:choose>
					<c:choose>
					    <c:when test="${user.hasSellerDTO()==true}">
					       Paypal: <input type="text" name="paypal" value="${user.getSellerDTO().getPaypal()}">
					    </c:when>  
			    	</c:choose>
				       <input type="hidden" name="action" value="updating_user" onclick='validate()'/>
					  <input type='submit' value='Update' onclick='validate()'/>
			    </c:otherwise>
			</c:choose>
		    
		</form>
	</div>
</div>
<%@ include file="footer.jsp"%>
</body>
</html>
<script type="text/javascript">
    function validate() {
        var val = registration.password.value;
        if (val == null || val.trim() == "") {
            //alert('Please enter password.');
            registration.password.focus();
            return false; // cancel submission
        } else {
            document.registration.submit(); // allow submit
        }
    }
</script>