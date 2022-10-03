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
            <span style="font-size: 8px;">
                [user_details.jsp]
            </span>
            <p style="text-align: center; font-size: 22px;font-weight: bold">
                *** <fmt:message key="user_details_jsp.add_new_user"/> ***
            </p>
            <hr>
            <form action="controller" method="post">
                <div>
                    <span class="header_key">
                        <fmt:message key="data_login"/>
                    </span>
                    <input class="header_value" name="newLogin" value="${user.login}">
                    <input type="hidden" name="id" value="${user.id}">
                    <br>
                    <span class="header_key">
                        <fmt:message key="data_password"/>
                    </span>
                    <input class="header_value" name="newPassword" value="${user.password}">
                    <br>
                    <span class="header_key">
                        <fmt:message key="data_email"/>
                    </span>
                    <input class="header_value" name="newEmail" value="${user.email}" >
                    <br>
                    <span class="header_key">
                        <fmt:message key="data_role"/>
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
                  <button type="submit" name="command" value="command.add_user_details">
                        <fmt:message key="button_submit_ok"/>
                  </button>
                  <button type="submit" name="command" value="command.show_users">
                        <fmt:message key="button_submit_cancel"/>
                  </button>
               </div>
            </form>
        </div>
    </body>
</html>