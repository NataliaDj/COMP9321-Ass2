<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Search Users</title>
	<link href="frontpage.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@ include file="/Header.html" %>
<form action="ControllerServlet" method='POST'>
	<form>
		username<input type="text" name="username">
		<input type="hidden" name="operation" value="searchUsers"/>
		<input type='submit' value='Submit'/>
	</form>
</form>
<%@ include file="/footer.jsp" %>
</body>
</html>