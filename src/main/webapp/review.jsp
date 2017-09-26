<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Review Page!</title>
<link href="<c:url value="/resources/css/lib.css" />" rel="stylesheet">
</head>
<body>
	<%@ include file="header.jsp"%>
	<h3>${msg}</h3>

	<c:if test="${not empty list}">
		<table>
			<tr>
				<th>Title</th>
				<th>Author</th>
				<th>Reviewed By</th>
				<th>Review Date</th>
				<th>Review</th>
				<th>Rating</th>
			</tr>
			<br>
			<c:forEach var="list" items="${list}">
				<tr>
					<td>${list.title }</td>
					<td>${list.author }</td>
					<td>${list.userName}</td>
					<td>${list.reviewDate}</td>
					<td>${list.review}</td>
					<td>${list.rating }</td>
				</tr>
			</c:forEach>
			</c:if>
		</table>

<br>
Fetch by Author
<form action="/LibraryManager/reviewAuthor" method="post">
<input type="text" name="author"/>
<input type="Submit" value="Fetch"/>
</form>
<br>
OR
<br>
Fetch by Title
<form action="/LibraryManager/reviewTitle" method="post">
<input type="text" name="title"/>
<input type="Submit" value="Fetch"/>
</form>
</body>
</html>