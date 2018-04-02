<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

suggestloggingin
<c:choose>
    <c:when test="${not empty currentUser}">
        ${currentUser.name} is logged in

        <form:form method="post">
            <input type="submit" name="_eventId_continue" value="Continue"/>
        </form:form>
    </c:when>
    <c:otherwise>
        <c:import url="/login"/>
        <form:form method="post">
            <input type="submit" name="_eventId_orderAsGuest" value="Order as a guest"/>
            <a href="${flowExecutionUrl}&_eventId=cancel">Cancel</a>
        </form:form>
    </c:otherwise>
</c:choose>



