<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
<title>Pizzeria</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="<c:url value="/css/main.css"/>">
</head>
<body>
    <header id="showcase" class="grid">
        <div class="bg-image"></div>
        <div id="user-login">
            <c:if test="${not empty customer}">
                Welcome, <a href="<c:url value="/profile"></c:url>" class="link">${customer.firstName}</a>
            </c:if>
            <c:if test="${empty customer}"><%@ include file="WEB-INF/fragments/logIn.jspf"%></c:if>
            <c:if test="${not empty customer or not empty user}">
                <a href="<c:url value="/log-out"/>" class="btn">Log Out</a>
            </c:if>
        </div>

        <div class="content-wrap">
            <h1>Welcome to Pizzeria</h1>
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Beatae vel ab illo ratione impedit dolorem consectetur in aspernatur architecto natus.</p>
            <a href="<c:url value="/pizza-builder"/>" class="btn">Pizza Builder</a>        
        </div>

    </header>
    <main id="main">
        <section id="section-a">
                <h2 class="content-title">My Order</h2>
                <c:if test="${not empty order.orderItems}">
                    <a href="<c:url value="/checkout"/>" class="btn">Checkout</a>
                </c:if>
            <c:choose>
                <c:when test="${empty order.orderItems}">
                    <p>
                        The order is empty.
                    </p>
                </c:when>
                <c:otherwise>
                <ul class="grid">
                    <c:forEach items="${order.orderItems}" var="orderItem">
                        <li>
                            <div class="card">
                                <img alt="" src="images/pexels-photo-280453.jpeg">
                                <h3 class="card-title">
                                    ${orderItem.pizza.size.name} ${orderItem.pizza.crust.name} x ${orderItem.quantity} 
                                </h3>
                                <a href='<c:url value="/order/edit"><c:param name="orderItemId" value="${orderItem.id}"/></c:url>' class="btn">Edit</a>
                                <a href='<c:url value="/order/remove"><c:param name="orderItemId" value="${orderItem.id}"/></c:url>' class="btn">Remove</a>
                                <p>
                                    <c:choose>
                                        <c:when test="${orderItem.pizza.left.items eq orderItem.pizza.right.items}">
                                        <section>
                                            Whole: <br>
                                            <c:forEach items="${orderItem.pizza.left.items}" var="pizzaItem">
                                                ${pizzaItem.ingredient.name} x ${pizzaItem.quantity}
                                            </c:forEach>
                                            </section>
                                        </c:when>
                                        <c:otherwise>
                                            <section>
                                                Left: 
                                                <c:forEach items="${orderItem.pizza.left.items}" var="pizzaItem">
                                                    ${pizzaItem.ingredient.name} x ${pizzaItem.quantity}
                                                </c:forEach>
                                            </section>
                                            <section>
                                                Right: 
                                                <c:forEach items="${orderItem.pizza.right.items}" var="pizzaItem">
                                                    ${pizzaItem.ingredient.name} x ${pizzaItem.quantity}
                                                </c:forEach>
                                            </section>
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                            </div>
                        </li>
                    </c:forEach>
                    </ul>
                </c:otherwise>
            </c:choose>
        </section>
        <section id="section-b" class="grid">
            <div class="box">
                <h2 class="content-title">Our Address</h2>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquid saepe vel facere laudantium molestiae amet cumque rerum non animi ipsa.</p>
                <p>pizzeria@example.com</p>
            </div>
            <div class="box">
                <h2 class="content-title">About Us</h2>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Iusto magni temporibus doloribus excepturi ducimus pariatur labore minus eos! Cumque labore molestias iste non aliquid pariatur quis et aspernatur exercitationem nobis rem aliquam eos laudantium suscipit assumenda ut enim est repellat.</p>
            </div>
        </section>
        
    </main>
    
    <%@ include file="WEB-INF/fragments/footer.jspf" %>
</body>
</html>
