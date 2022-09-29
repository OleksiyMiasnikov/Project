<!DOCTYPE html>
<html>
<body>
        <%@ include file="header.jspf" %>
        <script src=js/pagination.js></script>
            <div class="dataBox">
                <input id="userRole" value="${employee.user.role.name}" hidden="hidden">
                <span style="font-size: 8px;">
                    [users_list.jsp]
                </span>
                <p style="text-align: center; font-size: 22px;font-weight: bold">
                    *** List of users ***
                </p>
                <hr>
                <div class=table_header>
                    <button type="submit"
                            name="command"
                            value="Delete user"
                            class="table_header"
                            style="width: 50px; color: black;">
                         <i class="fa-solid fa-trash-can"></i>
                    </button>
                    <span class=table_header style="width: 50px;">
                        Id
                    </span>
                    <span class=table_header style="width: 200px;">
                        User
                    </span>
                    <span class=table_header style="width: 150px;">
                        Role
                    </span>
                </div>
                <br>
                <hr>
                <div class=data_list>
                    <c:forEach var="item" items="${result}">
                        <input  type="checkbox"
                                class="item"
                                style="width: 50px;text-align:center;"
                                name="users"
                                id="myCheck"
                                value=${item.id}>
                        <span class="item" style="width: 50px;">
                             ${item.id}
                        </span>
                        <span class="item" style="width: 200px;text-align: center;">
                             <a href="controller?command='Update user'&selectedUser={item.id}">
                                ${item.login}
                             </a>
                        </span>
                        <span class="item" style="width: 150px;text-align: center;">
                            ${item.role.name}
                        </span>
                        <br>
                        <hr>
                    </c:forEach>
                </div>
                <br>
                <c:if test="${pages_total > 1}">
                    <%@ include file="pagination.jspf" %>
                </c:if>
            </div>
        </form>
        <script src="js/order.js"></script>
    </body>
</html>