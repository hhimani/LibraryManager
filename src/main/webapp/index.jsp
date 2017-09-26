<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page session="true"%>
<html>
<head>
<title>Library Manager</title>
<link href="<c:url value="/resources/css/lib.css" />" rel="stylesheet">
</head>
<body>
	<div class="container">
		<%@ include file="header.jsp" %>

		
		<h3>${msg}</h3>
		<p>
		<table>
			<tr>
				<a href="/LibraryManager/search"> <input type="Submit"
					value="Search Books" />
				</a>
			</tr>
			<tr>
				<a href="/LibraryManager/inventory"> <input type="Submit"
					value="Display Inventory" />
				</a>
			</tr>
				<tr>
				<a href="/LibraryManager/reviewPage"> <input type="Submit"
					value="Reviews" />
				</a>
			</tr>
		</table>
		<br> 
		<%@include file="footer.jsp" %>
		</p>
	</div>
</body>
</html>
