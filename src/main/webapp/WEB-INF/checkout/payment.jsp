<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    ${order.cost} is due. Click the "Pay" button to pay for the order.
    This is just a stub page. In theory, we could probably integrate Paypal or something similar.
    <form action="<c:url value="/checkout"/>" method="POST">
        <input type="hidden" name="page" value="payment">
        <input type="submit" name="action" value="pay">
        <input type="submit" name="action" value="back">
    </form>
</body>
</html>