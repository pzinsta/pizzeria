<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Order Tracker</title>
    </head>
    <body>
        ${order.trackNumber}

        <c:forEach items="${order.orderEvents}" var="orderEvent">
            ${orderEvent.occurredOn} ${orderEvent.orderEventType}
        </c:forEach>

        <c:if test="${not empty order.delivery}">
            Delivery
            ${order.delivery.status}
        </c:if>
    </body>
</html>
