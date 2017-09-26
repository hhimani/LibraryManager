<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List of Books</title>
</head>
<body>
<%@include file="header.jsp" %>
	<c:if test="${not empty list}">
		<table>
			<tr>
				<th>Title</th>
				<th>Author</th>
				<th>Issue Date</th>
				<th>Return Date</th>
				<th>Reviewed</th>
				<th>Review </th>
			</tr>
			<br>
			<c:forEach var="list" items="${list}">
				<tr>
					<td>${list.title }</td>
					<td>${list.author }</td>
					<td>${list.issueDate}</td>
					<td>${list.actualReturnDate}</td>
					<td>${list.reviewed}</td>
					<td>
					<c:choose>
					<c:when test="${list.actualReturnDate !=NULL && list.reviewed =='false'}">
					<form action="/LibraryManager/addReview" method=post>
					Enter Review :<input type="text" name="review"/><br>
					Rate out of 5 <input type="radio" name="rating" value="1"/> 1
  					<input type="radio" name="rating" value="2"/> 2
  					<input type="radio" name="rating" value="3"/> 3
  					<input type="radio" name="rating" value="4"> 4
  					<input type="radio" name="rating" value="5"/> 5
  					<br>
  					<input type="hidden" name="userName" value="${list.userName }" readonly="readOnly"/>
  					<input type="hidden" name="title" value="${list.title }" readonly="readonly"/>
  					<input type="hidden" name="author" value="${list.author }" readonly="readonly"/>
  					<br>
					<input type="Submit" value="Submit Review"/>
					</form>
					</c:when>
					<c:otherwise>
					<a href="/LibraryManager/reviewUser">Click to Goto Review Section</a>
					</c:otherwise>
					</c:choose>
					</td>
				</tr>
			</c:forEach>
			</c:if>
		</table>

<h3>Enter title of book to return </h3>
<form action="/LibraryManager/returnBook" method="post">
<input type="text" name="title"/>
<br>
<input type="submit" name="Return"/>

</form>
</body>
</html>