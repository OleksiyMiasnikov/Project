<!DOCTYPE html>
<html>
<body>
        <%@ include file="/jsp/header.jspf" %>
            <div class="dataBox">
                <input id="userRole" value="${employee.user.role.name}" hidden="hidden">
                <span style="font-size: 8px;">
                    [users_list.jsp]
                </span>
                <p style="text-align: center; font-size: 22px;font-weight: bold">
                    *** <fmt:message key="user_list_jsp.list_of_users"/> ***
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
                        <fmt:message key="user_list_jsp.id"/>
                    </span>
                    <span class=table_header style="width: 200px;">
                        <fmt:message key="user_list_jsp.user"/>
                    </span>
                    <span class=table_header style="width: 150px;">
                        <fmt:message key="user_list_jsp.role"/>
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
                             <a href="${pageContext.request.contextPath}/controller?command=Update user&selectedUser=${item.id}">
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
                    <%@ include file="/jsp/pagination.jspf" %>
                </c:if>
            </div>
        </form>
        <script src="js/order.js"></script>
    </body>
</html>