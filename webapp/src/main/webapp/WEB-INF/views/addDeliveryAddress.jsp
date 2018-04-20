<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Add delivery address</title>
    </head>
    <body>
        <form:form method="post" modelAttribute="deliveryAddressForm">
            <%@ include file="fragments/deliveryAddressFormFields.jspf" %>
            <input type="submit" value="Add">
        </form:form>
    </body>
</html>
