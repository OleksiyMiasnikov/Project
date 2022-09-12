<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <body>
        <%@ include file="header.jspf" %>

        <div style="margin-left:25%;padding:1px 16px;height:1000px;">
            <h2>Header</h2>
            <p>Some text..</p>
            <hr>
            1.
            ${result}
            <hr>
            2.
            ${sessionScope.result}
            <hr>
            3.
            <%=session.getAttribute("result") %>
            <hr>
            <jsp:useBean id="date" class="java.util.Date"></jsp:useBean>
            <c:out value="${date}"></c:out>
            <hr>
            <a href="index.html"> Back to authorization page !</a>
            <ul>
                <c:forEach var="k" begin="1" end="5">
                    <li>${k}</li>
                </c:forEach>
            </ul>

        </div>
    </body>
</html>