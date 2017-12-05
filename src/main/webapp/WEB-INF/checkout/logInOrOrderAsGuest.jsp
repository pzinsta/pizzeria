<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<c:url value="/css/main.css"/>">
<title>Log In</title>

<style>
    form div {
        text-align: left;
    }
</style>
</head>
<body>
    <div>
        <h2>Log In</h2>    
        <%@ include file="../fragments/logIn.jspf" %>
    </div>
    <form method="POST" action="<c:url value="/checkout"/>">
        <div>
        <label for="email">Email address</label>
        <input type="text" name="email" id="email" value="${unregisteredCustomer.email}">
        <span>${validationErrors.email}</span>
        </div>
        <div>
        <label for="first-name">First name</label>
        <input type="text" name="first-name" id="first-name" required="required" value="${unregisteredCustomer.firstName}">
        <span>${validationErrors.firstName}</span>
        </div>
        <div>
        <label for="last-name">Last name</label>
        <input type="text" name="last-name" id="last-name" value="${unregisteredCustomer.lastName}">
        <span>${validationErrors.lastName}</span>
        </div>
        <div>
        <label for="phone-number">Phone number</label>
        <input type="tel" name="phone-number" id="phone-number" required="required" value="${unregisteredCustomer.phoneNumber}">
        <span>${validationErrors.phoneNumber}</span>
        </div>
        <div>
        <label for="address">Address</label>
        <input type="text" name="address" id="address" required="required" value="${unregisteredCustomer.address}">
        <span>${validationErrors.address}</span>
        </div>
        <div>
        <input type="hidden" name="page" value="logInOrOrderAsGuest">
        <input type="submit" name="action" value="Order as a guest" class="btn">
        <input type="submit" name="action" value="Back" formnovalidate="formnovalidate" class="btn">
        </div>
    </form>
    
</body>
</html>