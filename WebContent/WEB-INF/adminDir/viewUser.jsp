<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title><c:out value="${username}"/></title>
	<link href="frontpage.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@ include file="/Header.html" %>
<c:out value="username: ${username}"/>
<%@ include file="/footer.jsp" %>
</body>
</html>