<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search</title>
<link href="<c:url value="/resources/css/lib.css" />" rel="stylesheet">
</head>
<body>


<%@ include file="header.jsp" %>

<%-- <h3>${msg}</h3> --%>
<h2>Search by Author</h2>
<form action="/LibraryManager/searchresults" method="post">
<table>
<tr>
<input type="text" name="author"/>
</tr>
<tr>
<input type="submit" value="Enter"/>
</tr>
<br>
OR
<br>
</table>
<h2>Search by Title</h2>
<table>
<tr>
<input type="text" name="title"/>
</tr>
<tr>
<input type="submit" value="Enter"/>
</tr>
</table>
</form>

<%@include file="footer.jsp" %>

</body>
</html>