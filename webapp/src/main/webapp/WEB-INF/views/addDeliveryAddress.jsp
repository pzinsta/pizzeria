<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Edit delivery address</title>
    </head>
    <body>
        <form:form method="post" modelAttribute="deliveryAddress">
            <form:label path="city">City:</form:label>
            <form:input path="city"/>
            <form:label path="street">Street:</form:label>
            <form:input path="street"/>
            <form:label path="houseNumber">House number:</form:label>
            <form:input path="houseNumber"/>
            <form:label path="apartmentNumber">Apartment number:</form:label>
            <form:input path="apartmentNumber"/>
            <input type="submit" value="Add">
        </form:form>
    </body>
</html>
