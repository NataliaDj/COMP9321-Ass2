<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<header>
    <h1>Big red letters</h1>
</header>
<div id="topNav">
        <ul>
	        <li><a href="/Ass2">Home</a></li>
	        <c:if test="${adminAccount != 'yes' && empty username}">
				<li><a href="ControllerServlet?operation=login">Login</a></li>
				<li><a href="ControllerServlet?operation=register">Register</a></li>
			</c:if>
			<c:if test="${not empty username}">
				<li><a href="ControllerServlet?operation=register">Profile</a></li>
				<li><a href="ControllerServlet?operation=cart">Cart</a></li>
			</c:if>
			<c:if test="${adminAccount == 'yes'}">
				<li><a href="ControllerServlet?operation=searchUsers">Search Users</a></li>
			</c:if>
			<c:if test="${not empty username || adminAccount == 'yes'}">
				<li><a href="ControllerServlet?operation=logout">Logout</a></li>
 			</c:if>

        </ul>
</div>
</html>
