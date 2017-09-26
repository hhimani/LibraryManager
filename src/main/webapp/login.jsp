<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<link href="<c:url value="/resources/css/lib.css" />" rel="stylesheet">
</head>
<body>
<h3>${msg }</h3>
<form action="/LibraryManager/validateSignIn" method="post">

<div>
<label> UserName</label>
<input type="text" name="userName"/>
</div>
<div>
<label> Password</label>
<input type="password" name="password"/>
</div>
<input type="Submit" value="login"/>
</form>
<%@include file="footer.jsp" %>
</body>
</html>