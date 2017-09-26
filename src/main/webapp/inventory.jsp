<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<c:url value="/resources/css/lib.css" />" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inventory</title>
</head>
<body>

<div class="container">
<%@ include file="header.jsp" %>

<h3>Display inventory :- </h3>

<table>
<tr>
<th>ID</th>
<th>Title</th>
<th>Author</th>
<th>Copies</th>
<th>CopiesIssued</th>
<th>Rating</th>
</tr>
<br>
	<c:if test="${not empty list}">
	
			<c:forEach var="list" items="${list}">
			<tr>
				<td>${list.id}</td>
				<td>${list.title }</td>
				<td>${list.author }</td>
				<td>${list.copies }</td>
				<td>${list.copiesIssued }</td>
				<td>${list.rating }</td>
				</tr>
			</c:forEach>
	</c:if>
</table>

<p>
<h5>Choose what to do next</h5>
<table>
<tr> <a href="/LibraryManager/search">
<input type="Submit" value="Search Books"/>
</a></tr>
<tr> <a href="/LibraryManager/">
<input type="Submit" value="Index Page"/>
</a></tr>
</table>
</p>
</body>
</html>