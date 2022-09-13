<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <body>
        <%@ include file="header.jspf" %>
        <div style="margin-left:25%;padding:1px 16px;height:1000px;">
            <h2>
                Adding new user
                <hr>
            </h2>
            <form action="addUser" method="post">
                  Login
                  <input name="newLogin" value="" required>
                  <br>
                  Password
                  <input type="newPassword" value="" name="password" required>
                  <br>
                  Email
                  <input name="newEmail" value="" >
                  <br>
                  Role
                  <select name="newRole" required>
                        <option disabled selected value> -- select an option -- </option>
                        <c:forEach var="item" items="${roles }">
                            <option value="${item.getName() }">${item.getName() }</option>
                        </c:forEach>
                   </select>
                  <hr>
                  <br>
                  <input type="submit" value="Create user">
                  <input type="submit" value="Cancel">
               </div>
            </form>
        </div>
    </body>
</html>