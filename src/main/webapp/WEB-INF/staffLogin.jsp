<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Staff login</title>
</head>
<body>
    <form action="<c:url value="/staff/login"/>" method="POST">
		<label for="email">Email</label>
		<input type="email" autofocus="autofocus" required="required" placeholder="you@example.com" name="email"> 
		<label for="password">Password</label>
		<input type="password" required="required" name="password" placeholder="********">
		<input type="submit" value="Login">	
	</form>

</body>
</html>