<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
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
        <c:url value="/login" var="loginUrl"/>
        <form:form action="${loginUrl}" method="post">
            <spring:url value="${requestScope['javax.servlet.forward.servlet_path']}" context="/"
                        var="returnUrl">
                <spring:param name="execution" value="${requestScope.flowExecutionKey}"/>
            </spring:url>
            ${returnUrl}
            <input type="hidden" name="returnUrl" value="${returnUrl}">

            <c:if test="${param.loginError != null}">
                <p>
                    Invalid username and password.
                </p>
            </c:if>
            <p>
                <label for="username">Username</label>
                <input type="text" id="username" name="username"/>
            </p>
            <p>
                <label for="password">Password</label>
                <input type="password" id="password" name="password"/>
            </p>
            <button type="submit" class="btn">Log in</button>
        </form:form>

        <spring:url value="/account/register" var="registrationUrl">
            <spring:param name="returnUrl" value="${returnUrl}"/>
        </spring:url>

        <a href="${registrationUrl}">Register</a>

        <form:form method="post">
            <input type="submit" name="_eventId_orderAsGuest" value="Order as a guest"/>
            <a href="${flowExecutionUrl}&_eventId=cancel">Cancel</a>
        </form:form>
    </c:otherwise>
</c:choose>



