<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Pizza Builder</title>
    </head>
    <body>
        <h1>Pizza Builder</h1>
        <form:form method="post" modelAttribute="pizzaBuilderForm">
            <form:errors/>
            <%@ include file="fragments/orderItemFormFields.jspf" %>
            <input type="submit" value="Submit">

        </form:form>

        <spring:url value="/resources/javascript/pizzaBuilder.js" var="pizzaBuilderJsUrl"/>

        <script src="${pizzaBuilderJsUrl}"></script>
    </body>
</html>
