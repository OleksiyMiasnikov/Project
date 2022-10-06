<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <%--
        <link rel="stylesheet" href="css/main.css">
        --%>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css"/>
    </head>
    <body>
        [error_page.jsp]
        <hr>
        <c:if test="${!empty error_message}">
            ${error_message}
        </c:if>
        <hr
        <c:if test="${!empty error_cause}">
            ${error_cause}
        </c:if>
        <hr>
    </body>
</html>