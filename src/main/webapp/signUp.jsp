<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign Up</title>
</head>
<body>

    <form action="<c:url value="/sign-up/submit"/>" method="POST">
        <label for="email">Email address</label>
        <input type="text" name="email" id="email" required="required" value="${customer.email}">
        <span>${validationErrors.email}</span>
        
        <label for="first-name">First name</label>
        <input type="text" name="first-name" id="first-name" required="required" value="${customer.firstName}">
        <span>${validationErrors.firstName}</span>
        
        <label for="last-name">Last name</label>
        <input type="text" name="last-name" id="last-name" value="${customer.lastName}">
        <span>${validationErrors.lastName}</span>
        
        <label for="phone-number">Phone number</label>
        <input type="tel" name="phone-number" id="phone-number" value="${customer.phoneNumber}">
        <span>${validationErrors.phoneNumber}</span>
        
        <label for="password">Password</label>
        <input type="password" placeholder="*******" name="password" id="password" required="required">
        <span>${validationErrors.password}</span>
        
        <label for="confirm-password">Confirm Password</label>
        <input type="password" placeholder="*******" name="confirm-password" id="confirm-password" required="required">   
        <span>${validationErrors.confirmPassword}</span>
        
        <input type="submit" value="Sign Up">
        <input type="reset" value="Reset">
        <a href="<c:url value="/"/>">Cancel</a>
        
    </form>
</body>
</html>