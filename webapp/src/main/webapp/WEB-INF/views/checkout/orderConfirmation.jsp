<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
    <head>

    </head>
    <body>

        <div>
            <c:forEach items="${cart.orderItems}" var="orderItem">
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
            </c:forEach>
        </div>
        <form:form method="post">
            <input type="hidden" name="_flowExecutionKey"
                   value="${flowExecutionKey}"/>
            <input type="submit" name="_eventId_edit" value="Edit"/>
            <input type="submit" name="_eventId_continue" value="Continue"/>
            <a href="${flowExecutionUrl}&_eventId=cancel">Cancel</a>
        </form:form>


    </body>
</html>
