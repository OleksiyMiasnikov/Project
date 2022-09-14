<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>
            body {
                margin: 50;
            }
            .sideBar {
                list-style-type: none;
                margin: 0;
                padding: 20px;
                width: 15%;
                background-color: #f1f1f1;
                position: fixed;
                height: 100%;
                overflow: auto;
            }
            .sideBar input {
                margin: 10px;
                padding 10px;
                align: center;
                width: 140px;
                background-color: #04AA6D;
                color: white;
                font-size: 16px;
                font-weight: 100;
                border: 1px solid #a7a8a8;
                border-radius: 5px 5px 5px 5px;
            }
            .sideBar input:hover {
                 background: #047e51;
             }
        </style>
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

           <div style="margin-left:25%;padding:1px 16px;height:1000px;">
                <style type="text/css">
                    ul {
                        list-style-type: none;
                    }
                </style>
                <ul>
                    <c:forEach var="user" items="${result}">
                        <li>
                            <input type="radio" id="radio" value="${user.login}" name="users" />
                            ${user.login}
                            :
                            ${user.role.name}
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </form>
    </body>
</html>