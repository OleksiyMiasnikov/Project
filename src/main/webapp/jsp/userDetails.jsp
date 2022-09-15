<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
   <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
   </head>
    <body>
        <%@ include file="header.jspf" %>
        <div class="dataBox">
            <h2>
                Adding new user
                <hr>
            </h2>
            <form action="addUser" method="post">
                  Login
                  <input name="newLogin" value="${user.login}">
                  <input type="hidden" name="id" value="${user.id}">
                  <br>
                  Password
                  <input name="newPassword" value="${user.password}">
                  <br>
                  Email
                  <input name="newEmail" value="${user.email}" >
                  <br>
                  Role
                  <select name="newRole">
                        <option value=${user.role.name}> ${user.role.name} </option>
                        <c:forEach var="item" items="${roles }">
                            <option value="${item.getName() }">
                                ${item.getName() }
                            </option>
                        </c:forEach>
                   </select>
                  <hr>
                  <br>
                  <input type="submit" name="button" value="Ok">
                  <input type="submit" name="button" value="Cancel">
               </div>
            </form>
        </div>
    </body>
</html>