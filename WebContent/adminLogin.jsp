<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Admin Login</title>
	<link href="frontpage.css" rel="stylesheet" type="text/css">
</head>
<body>
<h1>Admin Login</h1>
<form action="ControllerServlet" method='POST'>
	username<input type="text" name="adminId">
	password<input type="password" name="password">
	<input type="hidden" name="operation" value="adminLogin"/>
	<input type='submit' value='Submit'/>
</form>
<c:if test="${loginFailed == 'yes'}">
	<p>Login Failed!</p>
</c:if>
<%@ include file="footer.jsp" %>
</body>
</html>