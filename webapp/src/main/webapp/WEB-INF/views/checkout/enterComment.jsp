<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Enter a comment</title>
    </head>
    <body>
        <form:form modelAttribute="order">
            <form:label path="comment">Comment:</form:label>
            <form:textarea path="comment"/>
            <form:errors path="comment"/>
            <input type="submit" name="_eventId_continue" value="Continue">
        </form:form>
    </body>
</html>
