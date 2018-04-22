<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Write a review</title>
    </head>
    <body>
        ${order.trackNumber} ${order.status}

        <div>

            ${reviewForm.message} ${reviewForm.rating}
            <c:forEach items="${order.review.images}" var="image">
                <spring:url value="/file/{name}" var="imageUrl">
                    <spring:param name="name" value="${image.name}"/>
                </spring:url>
                <img src="${imageUrl}">
            </c:forEach>
        </div>

        <spring:url value="" var="actionUrl">
            <spring:param name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </spring:url>

        <form:form method="post" modelAttribute="reviewForm" action="${actionUrl}" enctype="multipart/form-data">
            <form:label path="message">Message:</form:label>
            <form:textarea path="message"/>
            <form:errors path="message"/>

            <form:label path="rating">Rating:</form:label>
            <form:select path="rating" items="${ratings}"/>
            <form:errors path="rating"/>

            <form:input path="images" type="file" multiple="multiple" accept="image/*"/>
            <form:errors path="images"/>

            <input type="submit">
        </form:form>
    </body>
</html>
