<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Reviews</title>
        <%@ include file="fragments/head.jspf" %>
        <spring:url value="/resources/css/starRating.css" var="starRatingUrl"/>
        <link rel="stylesheet" href="${starRatingUrl}">
    </head>
    <body>
        <div class="container">
            <%@ include file="fragments/navbar.jspf" %>

            <c:forEach items="${reviews}" var="review">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="panel-title">
                            <div title="Added on">
                                <javatime:format value="${review.createdOn}" style="MM"/>
                            </div>
                        </div>
                    </div>

                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="rating">
                                    <c:forEach begin="1" end="10" var="rating" varStatus="varStatus">
                                        <c:set value="${10 - rating + 1}" var="stars"/>
                                        <input type="radio" id="star${stars}"
                                               value="${stars}" ${review.rating eq stars ? 'checked' : ''} disabled>
                                        <label for="star${stars}" title="${stars} stars">${stars} stars</label>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="">
                                    <c:out value="${review.message}"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <c:forEach items="${review.images}" var="image">
                                <div class="col-xs-4 col-sm-2">
                                    <spring:url value="/file/{name}" var="imageUrl">
                                        <spring:param name="name" value="${image.name}"/>
                                    </spring:url>
                                    <a href="#" class="thumbnail" data-toggle="modal" data-target="#${image.name}">
                                        <img src="${imageUrl}"/>
                                    </a>
                                </div>
                                <div class="modal fade" id="${image.name}" tabindex="-1">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                            </div>
                                            <div class="modal-body">
                                                <div class="text-center">
                                                    <img src="${imageUrl}" class="img-thumbnail"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:forEach>

        </div>
        <%@ include file="fragments/footer.jspf" %>

    </body>
</html>
