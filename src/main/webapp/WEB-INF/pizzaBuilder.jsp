<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Build a pizza</title>
</head>
<body>
    <form method="POST">
        <label for="crust">Crust:</label>
        <select name="crust" id="crust">
            <c:forEach items="${crusts}" var="crust">
                <option value="${crust.id}">${crust.name}</option>
            </c:forEach>
        </select>
        <label for="pizzaSize">Size:</label>
        <select name="pizzaSize" id="pizzaSize">
            <c:forEach items="${pizzaSizes}" var="pizzaSize">
                <option value="${pizzaSize.id}">${pizzaSize.name}</option>
            </c:forEach>
        </select>
        <label for="quantity">Quantity:</label>
        <select name="quantity" id="quantity">
            <c:forEach begin="1" end="15" var="quantity">
                <option value="${quantity}">${quantity}</option>
            </c:forEach>
        </select>
<br>

        <c:forEach items="${ingredientsMap}" var="ingredientEntry">
            <label for="${ingredientEntry.key.name}">${ingredientEntry.key.name}</label>
            <br>
            <c:forEach items="${ingredientEntry.value}" var="ingredient">
                ${ingredient.name}
                <input type="radio" name="ingredient:${ingredient.id}" value="whole">
                <input type="radio" name="ingredient:${ingredient.id}" value="left">
                <input type="radio" name="ingredient:${ingredient.id}" value="right">
                <input type="checkbox" name="ingredient:${ingredient.id}" value="double"> 
                <input type="radio" name="ingredient:${ingredient.id}" value="none" checked="checked">
                <br>
            </c:forEach>
            <br>
        </c:forEach>

        <label for="bakeStyle">Bake style:</label>
        <select name="bakeStyle" id="bakeStyle">
            <c:forEach items="${bakeStyles}" var="bakeStyle">
                <option value="${bakeStyle.id}">${bakeStyle.name}</option>
            </c:forEach>
        </select>
        
        <label for="cutStyle">Cut style:</label>
        <select name="cutStyle" id="cutStyle">
            <c:forEach items="${cutStyles}" var="cutStyle">
                <option value="${cutStyle.id}">${cutStyle.name}</option>
            </c:forEach>
        </select>
        <input type="submit" value="add to cart">
    </form>
</body>
</html>