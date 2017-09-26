<%@taglib prefix="c"uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<link href="<c:url value="/resources/css/lib.css" />" rel="stylesheet">
</head>
<div style="float: right; margin-top: -10px">
	<c:choose>
		<c:when test="${empty user }">
			<a href="/LibraryManager/signin"> Sign in</a>
			<br>
			<a href="/LibraryManager/signup"> Sign up</a>
		</c:when>
		<c:otherwise>
			Hello ${user.userName }! &nbsp;
			<a href="/LibraryManager/logout">Logout</a>
			<br>
			<a href="/LibraryManager/profile">My Profile</a>
			<br>
			<img src="data:image/jpeg;base64,${image}" alt="..." width="50" height="50">
		</c:otherwise>
	</c:choose>
</div>
 
<div style="float: left; margin-top: -10px">
	<c:choose>
		<c:when test="${user.userName eq 'himani1349'}">
			<!-- who will feed the JSP the user value??MAV or session -->
			<a href="/LibraryManager/update"> Update Inventory </a>
			</c:when>
	</c:choose>
</div>

<h2>Welcome to library management tool</h2>