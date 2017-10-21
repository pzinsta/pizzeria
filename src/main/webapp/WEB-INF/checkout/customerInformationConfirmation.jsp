<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Confirm the customer information</title>
</head>
<body>
    <form method="POST" action="<c:url value="/checkout"/>">
        <label for="email">Email address</label>
        <input type="text" name="email" id="email" value="${customer.email}">
        <span>${validationErrors.email}</span>
        
        <label for="first-name">First name</label>
        <input type="text" name="first-name" id="first-name" required="required" value="${customer.firstName}">
        <span>${validationErrors.firstName}</span>
        
        <label for="last-name">Last name</label>
        <input type="text" name="last-name" id="last-name" value="${customer.lastName}">
        <span>${validationErrors.lastName}</span>
        
        <label for="phone-number">Phone number</label>
        <input type="tel" name="phone-number" id="phone-number" required="required" value="${customer.phoneNumber}">
        <span>${validationErrors.phoneNumber}</span>
        
        <label for="address">Address</label>
        <input type="text" name="address" id="address" required="required" value="${customer.address}">
        <span>${validationErrors.address}</span>
        
        <input type="hidden" name="page" value="customerInformationConfirmation">
        <input type="submit" name="action" value="continue">
    </form>
</body>
</html>