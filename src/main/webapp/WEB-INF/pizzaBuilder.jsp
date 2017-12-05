<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="<c:url value="/css/main.css"/>">
<link rel="stylesheet" href="<c:url value="/css/pizzaBuilder.css"/>">

<title>Build a pizza</title>
</head>
<body>

    <form method="POST">
        <%@ include file="fragments/pizzaOptions.jspf" %>
        <input type="submit" value="Add to cart" class="btn">
        <a href="<c:url value="/"/>" class="btn">Cancel</a>
    </form>
    
    <%@ include file="fragments/footer.jspf" %>
</body>


<script type="text/javascript" src="javascript/pizzaBuilder.js"></script>

</html>