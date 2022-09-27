<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/main.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css"/>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Oxanium"> <%-- Lekton | Metrophobic | Oxanium | Share Tech | Text Me One --%>
    </head>

    <body>
        <form action="controller" method="post">
            <div class="sideBar">
                [main_window.jsp]
                <br>
                <div class="employee">
                    ${Employee.user.login} (${Employee.user.role.name})
                </div>
                <br>
                <br>
                <c:forEach items="${Employee.menuItems}" var="item">
                   <input type="submit" value='${item}' id='${item}' name="command">
                   <br>
                </c:forEach>
                <br>
                <br>
                <br>
                <br>
                <input type="submit" value="Log out" name="command">
           </div>
           <div class="dataBox">
                <jsp:include page="${Fragment}" />
           </div>
        </form>
    </body>
</html>