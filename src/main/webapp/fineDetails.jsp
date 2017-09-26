<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fine History</title>
</head>
<body>
<%@ include file="header.jsp" %>
<h3>${msg}</h3>
	<c:if test="${not empty list}">
		<table>
			<tr>
				<th>Title</th>
				<th>Author</th>
				<th>Issue Date</th>
				<th>Return Date</th>
				<th>Expected Return Date</th>
				<th>Fine<th>
			</tr>
			<br>
			<c:forEach var="list" items="${list}">
				<tr>
					<td>${list.title }</td>
					<td>${list.author }</td>
					<td>${list.issueDate}</td>
					<td>${list.actualReturnDate}</td>
					<td>${list.expectedReturnDate}</td>
					<td>${list.fine}</td>
				</tr>
			</c:forEach>
			</c:if>
		</table>
		<br>So the total fine is ${fine }<br>
		
		<a href="/LibraryManager/generateFineCSV">
		Generate CSV
		</a>
</body>
</html>