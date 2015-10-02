<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="edu.unsw.comp9321.*"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Manage Your Listings</title>
	<link href="frontpage.css" rel="stylesheet" type="text/css">
	<link href="resultsTable.css" rel="stylesheet" type="text/css">
</head>
<body>

<%@ include file="HeaderSeller.html"%>

<div class="middleSection">    
	<div class= "middleSect">
	
		<!-- Table with form to pause listings -->
		<form method="post" action="ControllerServlet?operation=pause">
		<table class="results" width=100%>
			<tr>
				<td>
				Book Title
				</td>
				<td>
				Author
				</td>
				<td>
				Price
				</td>
				<td>
				Pause
				</td>
			</tr>
			<!-- Need to add in loop to show all of seller's listing -->
			<!-- Like the shopping cart -->
			
		</table>
		</form>
		
	</div>
</div>
<%@ include file="footer.jsp"%>

</body>
</html>