<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
<title>Pizzeria</title>
<link rel="stylesheet" href="<c:url value="/css/index.css"/>">
</head>
<body>
    <header>
        <h1>Welcome to Pizzeria</h1>
        Welcome,
        <c:choose>
            <c:when test="${empty customer}">Guest</c:when>
            <c:otherwise>${customer.firstName}</c:otherwise>
        </c:choose>
        <c:if test="${empty customer}"><%@ include file="WEB-INF/fragments/logIn.jspf" %></c:if>
    </header>
    <nav>
        <c:if test="${empty customer and empty user}">

            <a href="<c:url value="/sign-up"/>">sign up</a>

        </c:if>

        <a href="<c:url value="/pizza-builder"/>">pizza builder</a>
        <a href="<c:url value="/checkout"/>">checkout</a>
        <c:if test="${not empty customer or not empty user}">
            <a href="<c:url value="/log-out"/>">log out</a>
        </c:if>
    </nav>
    <main>
    <article></article>
    <aside>
        <div>
            <h2>My Order</h2>
            <c:choose>
                <c:when test="${empty order.orderItems}">
                    The order is empty.
                </c:when>
                <c:otherwise>
                    <c:forEach items="${order.orderItems}" var="orderItem">
                ${orderItem.pizza.size.name} ${orderItem.pizza.crust.name} x ${orderItem.quantity} 
                <a href='<c:url value="/order/remove"><c:param name="orderItemId" value="${orderItem.id}"/></c:url>'>remove</a>
                        <a href='<c:url value="/order/edit"><c:param name="orderItemId" value="${orderItem.id}"/></c:url>'>edit</a>
                        <c:choose>
                            <c:when test="${orderItem.pizza.left.items eq orderItem.pizza.right.items}">
                        Whole: 
                        <c:forEach items="${orderItem.pizza.left.items}" var="pizzaItem">
                            ${pizzaItem.ingredient.name} x ${pizzaItem.quantity}
                        </c:forEach>

                            </c:when>
                            <c:otherwise>
                        Left: 
                        <c:forEach items="${orderItem.pizza.left.items}" var="pizzaItem">
                            ${pizzaItem.ingredient.name} x ${pizzaItem.quantity}
                        </c:forEach>
                        
                        Right: 
                        <c:forEach items="${orderItem.pizza.right.items}" var="pizzaItem">
                            ${pizzaItem.ingredient.name} x ${pizzaItem.quantity}
                        </c:forEach>
                            </c:otherwise>
                        </c:choose>

                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </aside>
    </main>
    <footer>Copyright</footer>
</body>
</html>
