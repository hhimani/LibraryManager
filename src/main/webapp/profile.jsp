<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DashBoard</title>
<link href="<c:url value="/resources/css/lib.css" />" rel="stylesheet">
</head>
<body>
<%@ include file="header.jsp" %>

<div style="float: left; margin-top: 50px">
<h5>${uploadMsg} </h5>
<c:choose>
<%-- <img src="data:image/jpeg;base64,${image}" alt="..." width="200" height="200"> --%>
<c:when test="${empty image }">
	<form method="POST" action="/LibraryManager/uploadPic" enctype="multipart/form-data">
        File to upload: <input type="file" name="file"><br />  
        <input type="submit" value="Upload">
    </form>
    </c:when>
    <c:otherwise>
    <img src="data:image/jpeg;base64,${image}" alt="..." width="200" height="200">
    
    <form action="/LibraryManager/deletePic" method="post">
    <input type="hidden" name="userName" value="${user.userName}"/>
    <input type="submit" value="Delete"/>
    </form>
    
    <br>
    
    <form action="/LibraryManager/uploadPic" method="post" enctype="multipart/form-data">
    <input type="hidden" name="userName" value="${user.userName}"/>
    <input type="file" name="file"/>
    <input type="submit" value="Update"/>
    </form>
    </c:otherwise>
    </c:choose>
</div> 

<div style="float: center; margin-top: 100px;margin-left:100px;margin-right:100px">
<a href="/LibraryManager">Home</a>
<br>
<a href="/LibraryManager/reviewUser">Reviews</a>
<br>
<a href="/LibraryManager/finedetails">Fine History</a>
<br>
<a href="/LibraryManager/updateAccount"> Edit Profile</a>
<br>
<a href="/LibraryManager/issue">Issue Books</a>
<br>
<a href="/LibraryManager/listIssuedBooks"> List of Issued Books</a>
<br>
<a href="/LibraryManager/return"> Return</a>
</div>
<br>
</body>
</html>