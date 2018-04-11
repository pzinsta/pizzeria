<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
              crossorigin="anonymous">
        <title>Edit order</title>
    </head>
    <body>
        <div>
            <c:forEach items="${cart.orderItems}" var="orderItem" varStatus="varStatus">
                <div>${orderItem.pizza.size.name}</div>
                <div>${orderItem.pizza.bakeStyle.name}</div>
                <div>${orderItem.pizza.crust.name}</div>
                <div>${orderItem.pizza.cutStyle.name}</div>
                <div>
                    <c:forEach items="${orderItem.pizza.leftPizzaSide.pizzaItems}" var="pizzaItem">
                        <%@ include file="../fragments/pizzaItem.jspf" %>
                    </c:forEach>
                </div>
                <div>
                    <c:forEach items="${orderItem.pizza.rightPizzaSide.pizzaItems}" var="pizzaItem">
                        <%@ include file="../fragments/pizzaItem.jspf" %>
                    </c:forEach>
                </div>
                <div>${orderItem.pizza.cost} x ${orderItem.quantity} = ${orderItem.cost}</div>


                <spring:url value="${requestScope['javax.servlet.forward.servlet_path']}" context="/"
                            var="redirectToUrl">
                    <spring:param name="execution" value="${requestScope.flowExecutionKey}"/>
                </spring:url>

                <spring:url value="/order/orderItem/{orderItemId}/remove}" var="removeOrderItemUrl">
                    <spring:param name="orderItemId" value="${varStatus.index}"/>
                    <spring:param name="redirectTo" value="${redirectToUrl}"/>
                </spring:url>
                <a href="${removeOrderItemUrl}">Remove</a>

                <spring:url value="/order/orderItem/{orderItemId}/edit" var="editOrderItemUrl">
                    <spring:param name="orderItemId" value="${varStatus.index}"/>
                    <spring:param name="redirectTo" value="${redirectToUrl}"/>
                </spring:url>
                <a href="${editOrderItemUrl}">Edit</a>

            </c:forEach>

            <c:if test="${not empty cart.orderItems}">
                <spring:url value="/order/orderItem/clear" var="clearUrl">
                    <spring:param name="redirectTo" value="${redirectToUrl}"/>
                </spring:url>
                <a href="${clearUrl}">Clear</a>
            </c:if>
            <spring:url value="/builder" var="builderUrl">
                <spring:param name="redirectTo" value="${redirectToUrl}"/>
            </spring:url>
            <a href="${builderUrl}">Add</a>
        </div>

        <form:form method="post">

            <input type="submit" name="_eventId_continue" value="Continue"/>
        </form:form>


        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
                integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
                crossorigin="anonymous"></script>
    </body>
</html>
