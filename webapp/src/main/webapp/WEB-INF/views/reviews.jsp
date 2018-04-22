<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Reviews</title>
    </head>
    <body>
        <c:forEach items="${reviews}" var="review">
            <div>
                ${review.createdOn} ${review.lastUpdatedOn} ${review.rating} ${review.message}
                <c:forEach items="${review.images}" var="image">
                    <spring:url value="/file/{name}" var="imageUrl">
                        <spring:param name="name" value="${image.name}"/>
                    </spring:url>
                    <img src="${imageUrl}"/>
                </c:forEach>
            </div>
        </c:forEach>
    </body>
</html>
