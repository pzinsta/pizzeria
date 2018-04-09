<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
payment
${cart.order.cost}
${customer}${customer.email}${customer.firstName}
${order}
${deliveryAddress.street}
<form:form method="post">
    <input type="submit" name="_eventId_continue" value="Continue" />
    <a href="${flowExecutionUrl}&_eventId=cancel">Cancel</a>
</form:form>