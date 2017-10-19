<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Confirm the order</title>
</head>
<body>
    1
    ${order.orderItems}
    <form method="POST">
        
        <input type="submit" name="action" value="confirm">
        <a href="<c:url value="/"/>">cancel</a>
    </form>
</body>
</html>