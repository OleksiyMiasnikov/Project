<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

${applicationScope['menuTitle']}
<hr>

<form action="menuCommand" method="post">
    <c:forEach items="${applicationScope['menuItems']}" var="item">
       <input type="submit" value='${item}' name="menuButton">
       <br>
       <br>
    </c:forEach>
</form>
<form action="authorization" method="post">
    <input type="submit" value="Login">
</form>