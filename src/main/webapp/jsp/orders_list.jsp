<!DOCTYPE html>
<html>
<body>
        <%@ include file="/jsp/header.jspf" %>
            <div class="dataBox">
                <input id="userRole" value="${employee.user.role.name}" hidden="hidden">
                <span style="font-size: 8px;">
                    [orders_list.jsp]
                </span>
                <p class="header_title">
                    *** <fmt:message key="order_list_jst.list_of"/>
                    <fmt:message key="${operation}"/> ***
                </p>
                <hr>
                <span class="header_key" style="width: 200px;">
                    <fmt:message key="order_list_jst.quantity_of"/>
                    <fmt:message key="${operation}"/> :
                </span>
                <span>
                    ${total_quantity}
                </span>
                <br>
                <span class="header_key" style="width: 200px;">
                    <fmt:message key="data_total"/>
                    <fmt:message key="${operation}"/> :
                </span>
                <span>
                    ${total_amount}
                </span>
                <hr>
                <div class=table_header>
                    <button type="submit"
                            name="command"
                            id="delete_button"
                            hidden="hidden"
                            value="command.delete_order"
                            class="table_header"
                            style="width: 50px; color: black;">
                         <i class="fa-solid fa-trash-can"></i>
                    </button>
                    <span class=table_header style="width: 50px;">
                        <fmt:message key="data_id"/>
                    </span>
                    <span class=table_header style="width: 200px;">
                        <fmt:message key="data_time"/>
                    </span>
                    <span class=table_header style="width: 100px;">
                        <fmt:message key="data_amount"/>
                    </span>
                    <span class=table_header style="width: 100px;text-align: right;">
                        <fmt:message key="data_employee"/>
                    </span>
                </div>
                <br>
                <hr>
                <div class=data_list>
                    <c:forEach var="item" items="${result}">
                        <span class="item" id="checkSpan" style="width: 50px;text-align: center;" hidden="hidden">
                            <input  type="checkbox"
                                    style="width: 50px;text-align:center;"
                                    name="orders"
                                    id="myCheck"
                                    value=${item.id}>
                        </span>
                        <span class="item" style="width: 50px;text-align: center;">
                            <a href="controller?command=command.serve_order&id=${item.id}&operation=${operation}">
                                ${item.id}
                            </a>
                        </span>
                        <span class="item" style="width: 200px;text-align: center;">
                            ${item.date}
                        </span>
                        <span class="item" style="width: 100px;text-align: center;">
                            ${item.totalAmount}
                        </span>
                        <span class="item" style="width: 100px;text-align: right;">
                            ${item.user.login}
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
        <script src="${pageContext.request.contextPath}/js/order.js"></script>
    </body>
</html>