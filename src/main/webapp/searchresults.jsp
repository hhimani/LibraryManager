<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ page isELIgnored="false" %> --%>
 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Results</title>
<link href="<c:url value="/resources/css/lib.css" />" rel="stylesheet">
</head>
<body>
	<%@ include file="header.jsp" %>

<h3>${himani}</h3>

<table >
  <tr>
    <td>ID</td>
    <td>Title</td>
    <td>Author</td>
    <td>Copies</td>
    <td>Copies Issued</td>
    <td>Rating</td>
  </tr>
    <tr>
    <td>${b.id}</td>
    <td>${b.title}</td>
    <td>${b.author}</td>
    <td>${b.copies }</td>
    <td>${b.copiesIssued }</td>
    <td>${b.rating }</td>
  </tr>
  </table>
  <br>
  <br>
  <c:if test="${not empty list}">
 <h3>List of review for Title/Author  :-</h3><br>
		<table>
			<tr>
				<th>Title</th>
				<th>Author</th>
				<th>Reviewed By</th>
				<th>Review Date</th>
				<th>Review</th>
				<th>Rating</th>
			</tr>
			
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
			</table>
			</c:if>
  
  <h2>Choose what to do next!</h2>
  <table>
<tr> <a href="/LibraryManager/search">
<input type="Submit" value="Search Books"/>
</a></tr>
<tr> <a href="/LibraryManager/inventory">
<input type="Submit" value="Display Inventory"/>
</a>
</tr>
</table>
<%@include file="footer.jsp" %>
</body>
</html>