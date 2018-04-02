<%--@elvariable id="customer" type="pzinsta.pizzeria.model.user.Customer"--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

customerconfirmation

${customer.firstName}
${customer.lastName}
${customer.email}
${customer.phoneNumber}

<form:form method="post">
    <input type="submit" name="_eventId_continue" value="Continue" />
    <a href="${flowExecutionUrl}&_eventId=cancel">Cancel</a>
</form:form>

