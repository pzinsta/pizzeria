<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit a pizza</title>
</head>
<body>
 <form action='<c:url value="/order/edit"/>' method="POST">
    <%@ include file="fragments/pizzaOptions.jspf" %>
    <input type="hidden" name="orderItemId" value="${orderItem.id}">
    <input type="submit" value="Save">
 </form>
</body>
</html>