<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Reviews</title>
    </head>
    <body>
        <c:forEach items="${reviews}" var="review">
            <div>
                ${review.createdOn} ${review.lastUpdatedOn} ${review.rating} ${review.message}
            </div>
        </c:forEach>
    </body>
</html>
