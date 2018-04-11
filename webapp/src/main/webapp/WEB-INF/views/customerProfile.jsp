<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Customer profile</title>
    </head>
    <body>
        <form:form modelAttribute="customer" method="post">
            <form:label path="firstName">First name:</form:label>
            <form:input path="firstName"/>
            <form:label path="lastName">Last name:</form:label>
            <form:input path="lastName"/>
            <form:label path="email">Email:</form:label>
            <form:input path="email"/>
            <form:label path="phoneNumber">Phone number:</form:label>
            <form:input path="phoneNumber"/>

            <input type="submit"/>
        </form:form>

        <c:set value="${requestScope['javax.servlet.forward.servlet_path']}" var="returnUrl"/>

        <c:choose>
            <c:when test="${not empty customer.deliveryAddresses}">
                <c:forEach var="deliveryAddress" items="${customer.deliveryAddresses}" varStatus="varStatus">

                    <spring:url value="/customer/deliveryAddress/{deliveryAddressIndex}/remove" var="removeDeliveryAddressUrl">
                        <spring:param name="deliveryAddressIndex" value="${varStatus.index}"/>
                        <spring:param name="returnUrl" value="${returnUrl}"/>
                    </spring:url>

                    <spring:url value="/customer/deliveryAddress/{deliveryAddressIndex}" var="editDeliveryAddressUrl">
                        <spring:param name="deliveryAddressIndex" value="${varStatus.index}"/>
                        <spring:param name="returnUrl" value="${returnUrl}"/>
                    </spring:url>

                    <div>
                            ${deliveryAddress.city} ${deliveryAddress.street} ${deliveryAddress.houseNumber}
                            ${deliveryAddress.apartmentNumber}
                        <a href="${editDeliveryAddressUrl}">Edit</a> <a href="${removeDeliveryAddressUrl}">Remove</a>
                    </div>
                </c:forEach>
            </c:when>

            <c:otherwise>
                You don't have any delivery addresses.
            </c:otherwise>

        </c:choose>

        <c:choose>
            <c:when test="${not empty customer.orders}">
                <c:forEach items="${customer.orders}" var="order">
                    <spring:url value="/order/track/{trackNumber}" var="orderTrackerUrl">
                        <spring:param name="trackNumber" value="${order.trackNumber}"/>
                    </spring:url>
                    <a href="${orderTrackerUrl}">Track the order</a>
                    ${order.id} ${order.trackNumber}

                </c:forEach>
            </c:when>
            <c:otherwise>
                You don't have any orders.
            </c:otherwise>
        </c:choose>

        <spring:url value="/customer/deliveryAddress/add" var="addDeliveryAddressUrl"/>
        <a href="${addDeliveryAddressUrl}">Add a delivery address</a>
    </body>
</html>
