<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/main.css">
    </head>

    <body>
        <form action="menuCommand" method="post">
            <div class="sideBar">
                <c:forEach items="${sessionScope.menuItems}" var="item">
                   <input type="submit" value='${item}' id='${item}' name="menuButton">
                   <br>
                </c:forEach>
                <br>
                <br>
                <br>
                <br>
                <input type="submit" value="Log out" name="menuButton">
           </div>

           <div class="dataBox">
                <%-- ${result} --%>
                ${Fragment}
                <br>
                <jsp:include page="${Fragment}" />
            </div>
        </form>
    </body>
</html>