ordertypeSelection

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

${deliveryRequired}

<form:form method="post">
    <input type="submit" name="_eventId_carryOut" value="CarryOut"/>
    <input type="submit" name="_eventId_delivery" value="Delivery"/>
</form:form>