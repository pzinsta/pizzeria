<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Build a pizza</title>
</head>
<body>
    <form method="POST">
        <%@ include file="fragments/pizzaOptions.jspf" %>
        <input type="submit" value="add to cart">
    </form>
</body>
</html>