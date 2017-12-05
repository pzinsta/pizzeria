<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Confirm the order</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="<c:url value="/css/main.css"/>">

<style type="text/css">
    body {
        text-align: left;
    }
}
</style>
</head>
<body>
 
    <c:choose>
        <c:when test="${not empty order.orderItems}">
            <h1>Confirm the order</h1>
            <%@ include file="../fragments/orderItems.jspf" %>
            <form method="POST" action="<c:url value="/checkout"/>">
                <input type="hidden" name="page" value="confirm">
                <input type="submit" name="action" value="Confirm" class="btn">
                <a href="<c:url value="/"/>" class="btn">Cancel</a>
            </form>
        </c:when>
        <c:otherwise>
            <h1>You have not ordered anything.</h1>
            <a href="<c:url value="/"/>" class="btn">Main page</a>
        </c:otherwise>
    </c:choose>

</body>
</html>