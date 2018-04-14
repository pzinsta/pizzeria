<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Write a review</title>
    </head>
    <body>
        Enter a track number of the order you want to write a review for.

        <c:if test="${orderNotFound}">
            Order with the given track number was not found.
        </c:if>

        <spring:url value="/review/order" var="orderReviewSearchProcessingUrl"/>
        <form:form method="post" action="${orderReviewSearchProcessingUrl}">
            <input name="trackNumber"/>
            <input type="submit">
        </form:form>
    </body>
</html>
