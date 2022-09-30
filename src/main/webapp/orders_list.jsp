<!DOCTYPE html>
<html>
<body>
        <%@ include file="header.jspf" %>
        <script src=js/pagination.js></script>
            <div class="dataBox">
                <input id="userRole" value="${employee.user.role.name}" hidden="hidden">
                <span style="font-size: 8px;">
                    [orders_list.jsp]
                </span>
                <p style="text-align: center; font-size: 22px;font-weight: bold">
                    *** List of ${operation} ***
                </p>
                <hr>
                <span class="header_key" style="width: 200px;">
                    Quantity of ${operation} :
                </span>
                <span>
                    ${total_quantity}
                </span>
                <br>
                <span class="header_key" style="width: 200px;">
                    Total ${operation} amount :
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
                            value="Delete order"
                            class="table_header"
                            style="width: 50px; color: black;">
                         <i class="fa-solid fa-trash-can"></i>
                    </button>
                    <span class=table_header style="width: 50px;">
                        Id
                    </span>
                    <span class=table_header style="width: 200px;">
                        Time
                    </span>
                    <span class=table_header style="width: 100px;">
                        Amount
                    </span>
                    <span class=table_header style="width: 100px;">
                        Employee
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
                            <a href="controller?command=Serve order&id=${item.id}">
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
                    <%@ include file="pagination.jspf" %>
                </c:if>
            </div>
        </form>
        <script src="js/order.js"></script>
    </body>
</html>