<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Confirm the order</title>
</head>
<body>
    Confirm the order
    <br>
    <c:forEach items="${order.orderItems}" var="orderItem">
        <c:choose>
            <c:when test="${orderItem.pizza.left.name eq orderItem.pizza.right.name}">
                <c:out value="${orderItem.pizza.left.name}" default="custom"></c:out>
            </c:when>
            <c:otherwise>
                <c:out value="${orderItem.pizza.left.name}" default="custom"></c:out> / 
                <c:out value="${orderItem.pizza.right.name}" default="custom"></c:out>
            </c:otherwise>
        </c:choose> Pizza x ${orderItem.quantity} <br>
        Details <br>
        Crust: ${orderItem.pizza.crust.name} <br>
        Size: ${orderItem.pizza.size.name} <br>
        Bake style: ${orderItem.pizza.bakeStyle.name} <br>
        Cut style: ${orderItem.pizza.cutStyle.name} <br>

        <c:choose>
            <c:when test="${orderItem.pizza.left.items eq orderItem.pizza.right.items}">
                Whole <br>
                <c:set var="pizzaItems" value="${orderItem.pizza.left.items}" scope="request"></c:set>
                <%@ include file="fragments/pizzaSide.jspf"%>
            </c:when>
            <c:otherwise>
                Left <br>
                <c:set var="pizzaItems" value="${orderItem.pizza.left.items}" scope="request"></c:set>
                <%@ include file="fragments/pizzaSide.jspf"%>
                Left side's Cost: 
                ${orderItem.pizza.left.cost}
                
                Right <br>
                <c:set var="pizzaItems" value="${orderItem.pizza.right.items}" scope="request"></c:set>
                <%@ include file="fragments/pizzaSide.jspf"%>
                Right side's Cost: 
                ${orderItem.pizza.right.cost}
            </c:otherwise>
        </c:choose>
        Pizza's cost: 
        ${orderItem.pizza.cost}
    </c:forEach>
    Grand total:
    ${order.cost}
    <form method="POST">
        <input type="submit" name="action" value="confirm">
        <a href="<c:url value="/"/>">cancel</a>
    </form>
</body>
</html>