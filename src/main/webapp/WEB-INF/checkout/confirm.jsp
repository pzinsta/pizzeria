<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Confirm the order</title>
</head>
<body>

    <c:choose>
        <c:when test="${not empty order.orderItems}">
            
                Confirm the order
    <br>
            <%@ include file="../fragments/orderItems.jspf" %>
            <form method="POST" action="<c:url value="/checkout"/>">
                <input type="hidden" name="page" value="confirm">
                <input type="submit" name="action" value="confirm">
                <a href="<c:url value="/"/>">cancel</a>
            </form>
                
        </c:when>
        <c:otherwise>
            You have not ordered anything.
            <a href="<c:url value="/"/>">Main page</a>
        </c:otherwise>
    </c:choose>

</body>
</html>