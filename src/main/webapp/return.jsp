<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Return book</title>
<link href="<c:url value="/resources/css/lib.css" />" rel="stylesheet">
</head>
<body>

<%@ include file="header.jsp" %>

<h3>Enter title of book to return </h3>
<form action="/LibraryManager/returnBook" method="post">
<input type="text" name="title"/>
<br>
<input type="submit" name="Return"/>

</form>

</body>
</html>