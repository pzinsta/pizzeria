<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
enterGuestDeliveryAddress

<form:form method="post" modelAttribute="deliveryAddress">
    <%@ include file="../fragments/deliveryAddressFormFields.jspf" %>
    <input type="submit" name="_eventId_continue" value="Continue">
</form:form>