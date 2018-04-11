<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Track your order</title>
    </head>
    <body>

        <c:if test="${trackNumberNotFound}">
            Track number was not found.
        </c:if>
        <spring:url value="/order/track" var="processOrderTrackerSearchUrl"/>
        <form:form method="post" action="${processOrderTrackerSearchUrl}">
            <input name="trackNumber"/>
            <input type="submit">
        </form:form>
    </body>
</html>
