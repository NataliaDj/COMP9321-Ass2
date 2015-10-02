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

<div class="middleSection">    
	<div class= "middleSect">
	<table style="width:100%" cellpadding="10">
   	<tr>
   		<td>
   			Title
   		</td>
   		<td>
   			${publication.title}
   		</td>
   	</tr>		
   	<tr>
		<td>
			Author(s)
		</td>
		<td>
			<c:forEach var="i" items="${publication.author}" varStatus="loop">
   				${i}
   				${!loop.last ? ',' : ''}
   			</c:forEach>
   		</td>
   	</tr>
   	<tr>
		<td>
			Editor(s)
		</td>
		<td>
			<c:forEach var="i" items="${publication.editor}" varStatus="loop">
   				${i}
   				${!loop.last ? ',' : ''}
   			</c:forEach>
   		</td>
   	</tr>
   	<tr>
   		<td>
   			Book Title
   		</td>
   		<td>
   			${publication.booktitle}
   		</td>
   	</tr>
   	<tr>
		<td>
			Pages
		</td>
		<td>
			${publication.pages}
   		</td>
   	</tr>
   	<tr>
		<td>
			Year
		</td>
		<td>
			${publication.year}
   		</td>
   	</tr>
   	<tr>
		<td>
			Address
		</td>
		<td>
			${publication.address}
   		</td>
   	</tr>
   	<tr>
		<td>
			Journal
		</td>
		<td>
			${publication.journal}
   		</td>
   	</tr>
   	<tr>
		<td>
			Volume
		</td>
		<td>
			${publication.volume}
   		</td>
   	</tr>
   	<tr>
		<td>
			Number
		</td>
		<td>
			${publication.number}
   		</td>
   	</tr>
   	<tr>
		<td>
			Month
		</td>
		<td>
			${publication.month}
   		</td>
   	</tr>
   	<tr>
   		<td>
   			URL
   		</td>
   		<td>
   			${publication.url}
   		</td>
   	</tr>
   	<tr>
		<td>
			ee
		</td>
		<td>
			${publication.ee}
   		</td>
   	</tr>
   	<tr>
		<td>
			cdrom
		</td>
		<td>
			${publication.cdrom}
   		</td>
   	</tr>
   	<tr>
		<td>
			cite
		</td>
		<td>
			${publication.cite}
   		</td>
   	</tr>
   	<tr>
		<td>
			Publisher
		</td>
		<td>
			${publication.publisher}
   		</td>
   	</tr>
   	<tr>
		<td>
			Note
		</td>
		<td>
			${publication.note}
   		</td>
   	</tr>
   	<tr>
		<td>
			Crossref
		</td>
		<td>
			${publication.crossref}
   		</td>
   	</tr>
   	<tr>
		<td>
			ISBN
		</td>
		<td>
			${publication.isbn}
   		</td>
   	</tr>
   	<tr>
		<td>
			Series
		</td>
		<td>
			${publication.series}
   		</td>
   	</tr>
   	<tr>
		<td>
			School
		</td>
		<td>
			${publication.school}
   		</td>
   	</tr>
   	<tr>
		<td>
			Chapter
		</td>
		<td>
			${publication.chapter}
   		</td>
   	</tr>
  	</table>
	</div>
</div>
</body>
<%@ include file="footer.jsp"%>
</html>