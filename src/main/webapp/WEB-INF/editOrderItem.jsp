<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="<c:url value="/css/main.css"/>">
<link rel="stylesheet" href="<c:url value="/css/pizzaBuilder.css"/>">
<title>Edit a pizza</title>
</head>
<body>
 <form action='<c:url value="/order/edit"/>' method="POST">
    <%@ include file="fragments/pizzaOptions.jspf" %>
    <input type="hidden" name="orderItemId" value="${orderItem.id}">
    <input type="submit" value="Save" class="btn">
 </form>
</body>
<script type="text/javascript" src="<c:url value="/javascript/pizzaBuilder.js"></c:url>"></script>
</html>