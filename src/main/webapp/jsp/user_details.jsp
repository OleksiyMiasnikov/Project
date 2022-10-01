<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
   <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
   </head>
    <body>
        <%@ include file="/jsp/header.jspf" %>
        <div class="dataBox">
            <h2>
                Adding new user [user_details.jsp]
                <hr>
            </h2>
            <form action="controller" method="post">
                <div>
                    <span class="header_key">
                        Login
                    </span>
                    <input class="header_value" name="newLogin" value="${user.login}">
                    <input type="hidden" name="id" value="${user.id}">
                    <br>
                    <span class="header_key">
                        Password
                    </span>
                    <input class="header_value" name="newPassword" value="${user.password}">
                    <br>
                    <span class="header_key">
                        Email
                    </span>
                    <input class="header_value" name="newEmail" value="${user.email}" >
                    <br>
                    <span class="header_key">
                        Role
                    </span>
                    <select class="header_value" name="newRole" value="${user.role.name}">
                    <option> ${user.role.name} </option>
                    <c:forEach var="item" items="${roles }">
                        <option value="${item.getName() }">
                            ${item.getName() }
                        </option>
                    </c:forEach>
                    </select>
                </div>
                  <hr>
                  <br>
                  <button type="submit" name="command" value="AddUserDetails">Ok</button>
                  <button type="submit" name="command" value="Show users">Cancel</button>
               </div>
            </form>
        </div>
    </body>
</html>