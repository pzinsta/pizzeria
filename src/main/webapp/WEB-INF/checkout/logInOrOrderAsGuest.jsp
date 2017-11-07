<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Log In</title>
</head>
<body>
    Log In    
    
    <%@ include file="../fragments/logIn.jspf" %>
    
    <form method="POST" action="<c:url value="/checkout"/>">
        <label for="email">Email address</label>
        <input type="text" name="email" id="email" value="${unregisteredCustomer.email}">
        <span>${validationErrors.email}</span>
        
        <label for="first-name">First name</label>
        <input type="text" name="first-name" id="first-name" required="required" value="${unregisteredCustomer.firstName}">
        <span>${validationErrors.firstName}</span>
        
        <label for="last-name">Last name</label>
        <input type="text" name="last-name" id="last-name" value="${unregisteredCustomer.lastName}">
        <span>${validationErrors.lastName}</span>
        
        <label for="phone-number">Phone number</label>
        <input type="tel" name="phone-number" id="phone-number" required="required" value="${unregisteredCustomer.phoneNumber}">
        <span>${validationErrors.phoneNumber}</span>
        
        <label for="address">Address</label>
        <input type="text" name="address" id="address" required="required" value="${unregisteredCustomer.address}">
        <span>${validationErrors.address}</span>
        
        <input type="hidden" name="page" value="logInOrOrderAsGuest">
        <input type="submit" name="action" value="order as a guest">
        <input type="submit" name="action" value="back" formnovalidate="formnovalidate">
    </form>
</body>
</html>