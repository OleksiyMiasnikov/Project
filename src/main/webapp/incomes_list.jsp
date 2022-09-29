<!DOCTYPE html>
<html>
<body>
        <%@ include file="header.jspf" %>
        <script src=js/pagination.js></script>
            <div class="dataBox">
                <span style="font-size: 8px;">
                    [incomes_list.jsp]
                </span>
                <p style="text-align: center; font-size: 22px;font-weight: bold">
                    *** List of incomes ***
                </p>
                <hr>
                <span class="header_key" style="width: 200px;">
                    Quantity of incomes :
                </span>
                <span>
                    ${ordersTotal}
                </span>
                <br>
                <span class="header_key" style="width: 200px;">
                    Total incomes amount :
                </span>
                <span>
                    ${amountTotal}
                </span>
                <hr>
                <div class=table_header>
                    <span class=table_header style="width: 50px;">
                        Id
                    </span>
                    <span class=table_header style="width: 200px;">
                        Time
                    </span>
                    <span class=table_header style="width: 120px;">
                        Amount
                    </span>
                    <span class=table_header style="width: 130px;">
                        Employee
                    </span>
                </div>
                <br>
                <hr>
                <div class=data_list>
                    <c:forEach var="item" items="${result}">
                        <span class=item style="width: 50px;text-align: center;">
                            <a href="serveOrder?id=${item.id}">
                                ${item.id}
                            </a>
                        </span>
                        <span class=item style="width: 200px;text-align: center;">
                            ${item.date}
                        </span>
                        <span class=item style="width: 120px;text-align: center;">
                            ${item.totalAmount}
                        </span>
                        <span class=item style="width: 130px;text-align: right;">
                            ${item.user.login}
                        </span>
                        <br>
                        <hr>
                    </c:forEach>
                </div>
                <br>
                <c:if test="${page > 1}">
                    <%@ include file="pagination.jspf" %>
                </c:if>
            </div>
        </form>
    </body>
</html>