<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

enterGuestInformation

<form:form modelAttribute="customer" action="${flowExecutionUrl}">

    <form:label path="firstName">First name:</form:label>
    <form:input path="firstName"/>

    <form:label path="lastName">Last name:</form:label>
    <form:input path="lastName"/>

    <form:label path="email">Email:</form:label>
    <form:input path="email" type="email"/>

    <form:label path="phoneNumber">Phone number:</form:label>
    <form:input path="phoneNumber"/>

    <input type="submit" name="_eventId_continue" value="Continue" />
    <a href="${flowExecutionUrl}&_eventId=cancel">Cancel</a>
</form:form>