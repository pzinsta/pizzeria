<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Pizza Builder</title>
    </head>
    <body>
        <h1>Pizza Builder</h1>

        <form:form method="post" modelAttribute="pizzaBuilderForm">
            <div>
                <form:label path="crustId">Crust:</form:label>
                <form:select path="crustId">
                    <form:options items="${crusts}" itemLabel="name" itemValue="id"/>
                </form:select>
            </div>
            <div>
                <form:label path="pizzaSizeId">Size:</form:label>
                <form:select path="pizzaSizeId">
                    <form:options items="${pizzaSizes}" itemLabel="name" itemValue="id"/>
                </form:select>
            </div>
            <div>
                <form:label path="quantity">Quantity:</form:label>
                <form:select path="quantity">
                    <form:options items="${quantities}"/>
                </form:select>
            </div>
            <div>
                <form:label path="bakeStyleId">Bake style:</form:label>
                <form:select path="bakeStyleId">
                    <form:options items="${bakeStyles}" itemLabel="name" itemValue="id"/>
                </form:select>
            </div>
            <div>
                <form:label path="cutStyleId">Cut style:</form:label>
                <form:select path="cutStyleId">
                    <form:options items="${cutStyles}" itemLabel="name" itemValue="id"/>
                </form:select>
            </div>


            <div>
                <c:forEach items="${pizzaBuilderForm.ingredientGroups}" var="ingredientGroup" varStatus="ingredientGroupVarStatus">
                    <div>${ingredientGroup.ingredientType.name}</div>
                    <form:hidden path="ingredientGroups[${ingredientGroupVarStatus.index}].ingredientType.id" />
                    <form:hidden path="ingredientGroups[${ingredientGroupVarStatus.index}].ingredientType.name" />
                    <c:forEach items="${ingredientGroup.ingredientQuantities}" var="ingredientQuantity" varStatus="ingredientQuantityVarStatus">
                        <div>
                            <div>
                                    ${ingredientQuantity.ingredient.name}
                            </div>
                            <c:set var="ingredientQuantityPath"
                                   value="ingredientGroups[${ingredientGroupVarStatus.index}].ingredientQuantities[${ingredientQuantityVarStatus.index}]"/>
                            <div>
                                <form:radiobuttons path="${ingredientQuantityPath}.ingredientSide" />
                                <form:checkbox path="${ingredientQuantityPath}.x2" label="x2" disabled="true" />
                            </div>
                            <form:hidden path="${ingredientQuantityPath}.ingredient.id"/>
                            <form:hidden path="${ingredientQuantityPath}.ingredient.name"/>

                        </div>
                    </c:forEach>
                </c:forEach>

            </div>

            <input type="submit" value="Submit">

        </form:form>

        <script src="resources/javascript/pizzaBuilder.js"></script>
    </body>
</html>
