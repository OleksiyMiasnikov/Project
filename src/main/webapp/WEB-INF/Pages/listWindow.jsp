<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <body>
        <%@ include file="header.jspf" %>

        <div style="margin-left:25%;padding:1px 16px;height:1000px;">
            <h2>
                Hello!!!
                <br>
                ${sessionScope.menuTitle}
            </h2>
            <ul>
                <c:forEach var="user" items="${result}">
                    <li>${user.getLogin()} : ${user.getRole().getName()} </li>
                </c:forEach>
            </ul>
        </div>
    </body>
</html>