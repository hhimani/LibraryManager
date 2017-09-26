<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign-up Page!</title>
<link href="<c:url value="/resources/css/lib.css" />" rel="stylesheet">
</head>
<body>
<h3>${msg }</h3>

<form action="/LibraryManager/createAccount" method="post">

<div>
<label> UserName</label>
<input type="text" name="userName"/>
</div>
<div>
<label> Password</label>
<input type="password" name="password"/>
</div>
<div>
<label> First Name</label>
<input type="text" name="firstName"/>
</div>
<div>
<label> Last Name</label>
<input type="text" name="lastName"/>
</div>
<div>
<label> Age</label>
<input type="text" name="age"/>
</div>
<div>
<label> Email ID</label>
<input type="text" name="email"/>
</div>
<div>
<label> Address</label>
<input type="text" name="address"/>
</div>
<div>
<label> Mobile No</label>
<input type="text" name="mobileNo"/>
</div>
<input type="Submit" value="Create Account"/>
</form>

<%@include file="footer.jsp" %>

</body>
</html>