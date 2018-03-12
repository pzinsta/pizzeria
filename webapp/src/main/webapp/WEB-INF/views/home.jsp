<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
              crossorigin="anonymous">
        <title>Pizzeria</title>
    </head>
    <body>
        <div class="container">
            <div class="header clearfix">
                <nav>
                    <ul class="nav nav-pills pull-right">
                        <li role="presentation" class="active"><a href="#">Home</a></li>
                        <li role="presentation"><a href="#">About</a></li>
                        <li role="presentation"><a href="#">Contact</a></li>
                    </ul>
                </nav>
                <h3 class="text-muted">Pizzeria</h3>
            </div>

            <div class="jumbotron">
                <h1>Build your own pizza with our pizza builder</h1>
                <c:url var="builderUrl" value="/builder"/>
                <p><a class="btn btn-lg btn-success" href="${builderUrl}" role="button">Start building</a></p>
            </div>



            <div>
                <%--@elvariable id="order" type="pzinsta.pizzeria.model.order.Order"--%>
                <c:forEach items="${order.orderItems}" var="orderItem">
                    <div>${orderItem.pizza.size.name}</div>
                    <div>${orderItem.pizza.bakeStyle.name}</div>
                    <div>${orderItem.pizza.crust.name}</div>
                    <div>${orderItem.pizza.cutStyle.name}</div>
                    <div>
                        <c:forEach items="${orderItem.pizza.left.pizzaItems}" var="pizzaItem">
                            <%@ include file="fragments/pizzaItem.jspf" %>
                        </c:forEach>
                    </div>
                    <div>
                        <c:forEach items="${orderItem.pizza.right.pizzaItems}" var="pizzaItem">
                            <%@ include file="fragments/pizzaItem.jspf" %>
                        </c:forEach>
                    </div>
                    <div>${orderItem.pizza.cost} x ${orderItem.quantity} = ${orderItem.cost}</div>
                </c:forEach>
            </div>

            <footer class="footer">
                <p>Made by pzinsta</p>
            </footer>
        </div> <!-- /container -->


        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
                integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
                crossorigin="anonymous"></script>
    </body>
</html>