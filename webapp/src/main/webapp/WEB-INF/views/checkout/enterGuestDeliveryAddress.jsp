<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
enterGuestDeliveryAddress

<form:form method="post" modelAttribute="deliveryAddress">
    <form:label path="city">City:</form:label>
    <form:input path="city"/>
    <form:label path="street">Street:</form:label>
    <form:input path="street"/>
    <form:label path="houseNumber">House number:</form:label>
    <form:input path="houseNumber"/>
    <form:label path="apartmentNumber">Apartment number:</form:label>
    <form:input path="apartmentNumber"/>
    <input type="submit" name="_eventId_continue" value="Continue">
</form:form>