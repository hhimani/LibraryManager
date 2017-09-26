<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update</title>
<link href="<c:url value="/resources/css/lib.css" />" rel="stylesheet">
</head>
<body>

<%@ include file="header.jsp" %>

<h2>Add Books to the Inventory</h2>
<form action="/LibraryManager/add" method="post">
<table>
<tr>
<td>
Title
</td>
<td>
Author
</td>
<td>
Copies
</td>
</tr>
<tr><td>
<input type="text" name="title"/>
</td>
<td>
<input type="text" name="author"/>
</td>
<td>
<input type="text" name="copies"/>
</td>
</tr>
<tr>
<input type="submit" value="Add"/>
</tr>
</table>
</form>
<br>
<h2>OR 
<br>
Delete books from the Inventory</h2>
<br>
<form action="/LibraryManager/delete" method="post">
<table>
<tr>
<td>
Title
</td>
<td>
Author
</td>
<td>
Copies
</td>
</tr>
<tr>
<td>
<input type="text" name="title"/>
</td>
<td>
<input type="text" name="author"/>
</td>
<td>
<input type="text" name="copies"/>
</td>
</tr>
<tr>
<input type="submit" value="Delete"/>
</tr>
</table>
</form>

<%@include file="footer.jsp" %>

</body>
</html>