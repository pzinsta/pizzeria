<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Register</title>
    </head>
    <body>
        <form:form method="post" modelAttribute="customerRegistrationForm">
            <form:label path="username">Username:</form:label>
            <form:input path="username"/>

            <form:label path="password">Password:</form:label>
            <form:password path="password"/>

            <form:label path="passwordAgain">Repeat password:</form:label>
            <form:password path="passwordAgain"/>

            <form:label path="firstName">First name:</form:label>
            <form:input path="firstName"/>

            <form:label path="lastName">Last name:</form:label>
            <form:input path="lastName"/>

            <form:label path="email">Email:</form:label>
            <form:input path="email" type="email"/>

            <form:label path="phoneNumber">Phone number:</form:label>
            <form:input path="phoneNumber"/>

            <input type="submit">
        </form:form>
    </body>
</html>
