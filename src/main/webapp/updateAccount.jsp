<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%@ include file="header.jsp" %>

${msg}
<div style="float: center; margin-top: 100px;margin-left:100px;margin-right:100px">
<c:choose>
<c:when test="${empty libUserDetails }">
<form action="/LibraryManager/updated" method="post">
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
</div><div>
<input type="Submit" value="Update Account"/>
</div>
</form>
</c:when>

<c:otherwise>
${libUserDetails.firstName}
<form action="/LibraryManager/updated" method="post">
<div>
<label> First Name</label>
<input type="text" name="firstName" value="${libUserDetails.firstName}"/>
</div>
<div>
<label> Last Name</label>
<input type="text" name="lastName" value="${libUserDetails.lastName}"/>
</div>
<div>
<label> Age</label>
<input type="text" name="age" value="${libUserDetails.age}"/>
</div>
<div>
<label> Email ID</label>
<input type="text" name="email" value="${libUserDetails.email}"/>
</div>
<div>
<label> Address</label>
<input type="text" name="address" value="${libUserDetails.address}"/>
</div>
<div>
<label> Mobile No</label>
<input type="text" name="mobileNo" value="${libUserDetails.mobileNo}"/>
</div>
<input type="submit" value="Update Account"/>
</form>
</c:otherwise>
</c:choose>
</div>
</body>

</html>