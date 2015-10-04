<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><c:out value="${userDTO.username}" /></title>
<link href="frontpage.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@ include file="/header.jsp"%>
	<div class="middleSection">
		<div class="middleSect">
			<table>
				<tr>
					<td><b>Username:</b></td>
					<td><c:out value="${userDTO.username}" />
					<td>
				</tr>
				<tr>
					<td><b>Email:</b></td>
					<td><c:out value="${userDTO.email}" />
					<td>
				</tr>
				<tr>
					<td><b>First Name:</b></td>
					<td><c:out value="${userDTO.firstName}" />
					<td>
				</tr>
				<tr>
					<td><b>Last Name:</b></td>
					<td><c:out value="${userDTO.lastName}" />
					<td>
				</tr>
				<tr>
					<td><b>Birth Year:</b></td>
					<td><c:out value="${userDTO.birthYear}" />
					<td>
				</tr>
				<tr>
					<td><b>Address:</b></td>
					<td><c:out value="${userDTO.addressString}" />
					<td>
				</tr>
				<tr>
					<td><b>Account Activated:</b></td>
					<td><c:out value="${userDTO.accountActivated}" />
					<td>
				</tr>
				<tr>
					<td><b>Account Banned:</b></td>
					<td><c:out value="${userDTO.ban}" />
					<td>
				</tr>
			</table>

			<c:choose>
				<c:when test="${userDTO.ban == false}">
					<a href="ControllerServlet?operation=banUser">Ban User</a>
				</c:when>
				<c:when test="${userDTO.ban == true}">
					<a href="ControllerServlet?operation=unbanUser">Unban User</a>
					<br>
				</c:when>
			</c:choose>
			<c:if test="${userDTO.buyerDTO != null}">
				<a href="ControllerServlet?operation=viewHistory">View User
					History</a>
			</c:if>
		</div>
	</div>
	<%@ include file="/footer.jsp"%>
</body>
</html>