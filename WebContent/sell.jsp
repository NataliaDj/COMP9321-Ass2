
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="edu.unsw.comp9321.*"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Sell a book</title>
	<link href="frontpage.css" rel="stylesheet" type="text/css">
</head>

<body>
<%@ include file="header.jsp"%>

<div class="middleSection">    
	<div class= "middleSect">
	
		<!-- Form to add new book to database -->
		<form action='ControllerServlet?operation=sell' class='sellForm' method='POST'>
			<h4>All fields with * are required</h4>
			
			<h3>Basic info</h3>
			Title*:	<input type="text" name="bookTitle">
			Author*:	<input type="text" name="bookAuthor">
			Price*:	<input type="text" name="bookPrice">
			
			<br/>
			<h3>Publication info</h3>
		   	Publication Type*: 
		   	<br/> 
		   	<input type="radio" name="bookType"  value="book/collection" checked>book/collection
		   	<input type="radio" name="bookType"  value="journal">journal
		   	<input type="radio" name="bookType"  value="conference">conference
		   	<input type="radio" name="bookType"  value="editorship">editorship
		   	<br/>
		    Publication Year:	<input type="text" name="bookYear">
		   	ISBN:	<input type="text" name="bookISBN">
		  	
		  	<h3>Selling info</h3>
		  	Picture: <input type="text" name="bookPicture">
		  	<h4>Please only give a url to a picture</h4>
		  	<br/>
	        <h4>Sell immediately?</h4>
	        <input type="radio" name="bookPause"  value="Yes" checked>Yes
	        <input type="radio" name="bookPause"  value="No">No
	        <br/>
	        <br/>
	        <br/>
	        <input type="hidden" name="bookSeller" value=${user.getUsername()}>
	        <input type="hidden" name="action" value="newListing"/>
	        <input type="submit">
		</form>
	</div>
</div>
<%@ include file="footer.jsp"%>

</body>
</html>