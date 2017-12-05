<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="<c:url value="/css/main.css"/>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${customer.firstName}&apos;s profile</title>

<style type="text/css">
    div {
        display: table-caption;
        padding: 1em;
    }
</style>
</head>
<body>
    <form method="POST">
        <input type="hidden" name="id" id="id" value="${customer.id}">
        
        <div>
            <label for="email">Email address</label>
            <input type="text" name="email" id="email" required="required" value="${customer.email}">
            <span>${validationErrors.email}</span>
        </div>
        
        <div>
            <label for="first-name">First name</label>
            <input type="text" name="first-name" id="first-name" required="required" value="${customer.firstName}">
            <span>${validationErrors.firstName}</span>
        </div>
        
        <div>
            <label for="last-name">Last name</label>
            <input type="text" name="last-name" id="last-name" value="${customer.lastName}">
            <span>${validationErrors.lastName}</span>
        </div>
        
        <div>
            <label for="phone-number">Phone number</label>
            <input type="tel" name="phone-number" id="phone-number" value="${customer.phoneNumber}">
            <span>${validationErrors.phoneNumber}</span>
        </div>
        
        <div>
            <label for="address">Address</label>
            <input type="text" name="address" id="address" value="${customer.address}">
            <span>${validationErrors.address}</span>
        </div>
        
        <input type="submit" value="Save" class="btn">
        <a href="<c:url value="/"/>" class="btn">Cancel</a>
    </form>
</body>
</html>