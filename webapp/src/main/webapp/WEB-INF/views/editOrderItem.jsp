<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Edit order item</title>
    </head>
    <body>
        <h1>Edit order item</h1>

        <form:form method="post" modelAttribute="pizzaBuilderForm">
            <%@ include file="fragments/orderItemFormFields.jspf" %>

            <input type="submit" value="Save">

        </form:form>


        <spring:url value="/resources/javascript/pizzaBuilder.js" var="pizzaBuilderJsUrl"/>

        <script src="${pizzaBuilderJsUrl}"></script>
    </body>
</html>
