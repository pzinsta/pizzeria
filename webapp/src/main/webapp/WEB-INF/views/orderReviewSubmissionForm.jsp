<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Write a review</title>
    </head>
    <body>
        ${order.trackNumber} ${order.status}
        <form:form method="post" modelAttribute="reviewForm">
            <form:label path="message">Message:</form:label>
            <form:textarea path="message"/>
            <form:errors path="message"/>

            <form:label path="rating">Rating:</form:label>
            <form:select path="rating" items="${ratings}"/>
            <form:errors path="rating"/>

            <input type="submit">
        </form:form>
    </body>
</html>
