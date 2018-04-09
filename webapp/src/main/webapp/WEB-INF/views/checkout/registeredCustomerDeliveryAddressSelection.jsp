registeredCustomerDeliveryAddressSelection

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<spring:url value="${requestScope['javax.servlet.forward.servlet_path']}" context="/"
            var="returnUrl">
    <spring:param name="execution" value="${requestScope.flowExecutionKey}"/>
</spring:url>

<form:form method="post"><%--@elvariable id="customer" type="pzinsta.pizzeria.model.user.Customer"--%>
    <c:forEach var="deliveryAddress" items="${customer.deliveryAddresses}" varStatus="varStatus">
        ${deliveryAddress.city} ${deliveryAddress.street} ${deliveryAddress.houseNumber}
        ${deliveryAddress.apartmentNumber}

        <input type="radio" name="deliveryAddressIndex" value="${varStatus.index}">

        <spring:url value="/customer/deliveryAddress/{deliveryAddressIndex}/remove" var="removeDeliveryAddressUrl">
            <spring:param name="deliveryAddressIndex" value="${varStatus.index}"/>
            <spring:param name="returnUrl" value="${returnUrl}"/>
        </spring:url>

        <spring:url value="/customer/deliveryAddress/{deliveryAddressIndex}" var="editDeliveryAddressUrl">
            <spring:param name="deliveryAddressIndex" value="${varStatus.index}"/>
            <spring:param name="returnUrl" value="${returnUrl}"/>
        </spring:url>

        <a href="${editDeliveryAddressUrl}">Edit</a> <a href="${removeDeliveryAddressUrl}">Remove</a>
    </c:forEach>

    <spring:url value="/customer/deliveryAddress/add" var="addDeliveryAddressUrl">
        <spring:param name="returnUrl" value="${returnUrl}"/>
    </spring:url>

    <a href="${addDeliveryAddressUrl}">Add a delivery address</a>

    <input type="submit" name="_eventId_continue" value="Continue"/>
</form:form>