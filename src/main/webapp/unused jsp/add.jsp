<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h2>Added to the inventory,Choose what to do next</h2>
<p>
<%-- <img src="${books}"/> --%>
<table>
<tr> <a href="/LibraryManager/search">
<input type="Submit" value="Search Books"/>
</a>
<tr> <a href="/LibraryManager/update" >
<input type="Submit" value="Update Books"/>
</a>
<tr> <a href="/LibraryManager/issuebook" >
<input type="Submit" value="Issue Books"/>
</a>
</table>
</p>
</body>
</html>