<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="pzinsta.pizzeria.model.order.OrderStatus" %>
<%@ page import="java.util.Arrays" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dashboard</title>
</head>
<body>

    <form>
        <select multiple="multiple" name="orderStatus">
            <c:forEach var="status" items="${OrderStatus.values()}">
                 <c:set scope="page" var="selected" value="false" />
                 <c:forEach var="selectedStatus" items="${paramValues.orderStatus}">
                    <c:if test="${selectedStatus eq status}">
                        <c:set var="selected" value="true"/>
                    </c:if>
                 </c:forEach>
                 <option ${selected ? "selected" : ""} value="${status}">${status.name()}</option>
            </c:forEach>
        </select>
        <input type="submit" value="Update">
    </form>
    <c:forEach items="${orders}" var="order">
        ${order.id}
        ${order.placed}
        <form method="POST" action="<c:url value="/staff/order/update-status"/>">
            <input type="hidden" name="orderId" value="${order.id}">
            <select name="statusId">
                <c:forEach var="status" items="${OrderStatus.values()}">
                    <option value="${status.ordinal()}" ${status eq order.status.name() ? "selected" : ""}>${status}</option>
                </c:forEach>
            </select>
            <input type="hidden" name="from" value="${requestScope['javax.servlet.forward.request_uri']}">
            <input type="submit" value="Update status">
        </form>
        ${order.cost}
        <br>
        ${order.customer.id}
        ${order.customer.firstName}
        ${order.customer.lastName}
        ${order.customer.address}
        ${order.customer.phoneNumber}
        ${order.customer.email}
        <br>
        
        <%@ include file="fragments/orderItems.jspf" %>
    </c:forEach>
    

    <a href="<c:url value="/log-out"/>">log out</a>
</body>
</html>