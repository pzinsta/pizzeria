<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${customer.firstName}&apos;s profile</title>
</head>
<body>
    <form method="POST">
        <input type="hidden" name="id" id="id" value="${customer.id}">
        
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
        
        <label for="address">Address</label>
        <input type="text" name="address" id="address" value="${customer.address}">
        <span>${validationErrors.address}</span>
        
        <input type="submit" value="Save">
        <a href="<c:url value="/"/>">Cancel</a>
    </form>
</body>
</html>