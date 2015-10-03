<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="edu.unsw.comp9321.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<header>
<h1>Big red letters</h1>
</header>
<div id="topNav">
	<ul>
		<li><a href="/Ass2">Home</a></li>
				<li><a href="ControllerServlet?operation=cart">Cart</a></li>
				<li><a href="ControllerServlet?operation=manage">Manage Listing</a></li>
				<li><a href="ControllerServlet?operation=sell">New Listing</a></li>

				<li><a href="ControllerServlet?operation=searchUsers">Search Users</a></li>

					<li><a href="ControllerServlet?operation=login">Login</a></li>
					<li><a href="ControllerServlet?operation=register">Register</a></li>

			<li><a href="ControllerServlet?operation=logout">Logout</a></li>
	</ul>
	</div>
</html>
