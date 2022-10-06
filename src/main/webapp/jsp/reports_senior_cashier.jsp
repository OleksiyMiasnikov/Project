<!DOCTYPE html>
<html>
<body>
        <%@ include file="/jsp/header.jspf" %>
        <script src=js/pagination.js></script>
            <div class="dataBox">
                <span style="font-size: 8px;">
                    [reports_senior_cashier.jsp]
                </span>
                <p style="text-align: center; font-size: 22px;font-weight: bold">
                    *** <fmt:message key="warehouse_list_jsp.remains_in_warehouse"/> ***
                </p>
                <hr>
                <span class="header_key" style="width: 170px;">
                    <fmt:message key="data_total_quantity"/> :
                </span>
                <span>
                    ${total_quantity}
                </span>
                <br>
                <span class="header_key" style="width: 170px;">
                    <fmt:message key="data_total_amount"/> :
                </span>
                <span>
                    ${total_amount}
                </span>
                <hr>
                <div class=table_header>
                    <span class=table_header style="width: 50px;">
                        <fmt:message key="data_id"/>
                        <button type="submit"
                                name="command"
                                id="delete_button"
                                value=""
                                class="table_header"
                                style="width: 10px; color: black; border:none;background-color: transparent;float: none;">
                                <i class="fa fa-sort" style="width: 20px;"></i>
                        </button>
                    </span>
                    <span class=table_header style="width: 200px;">
                        <fmt:message key="data_product_name"/>
                        <button type="submit"
                                name="command"
                                id="delete_button"
                                value=""
                                class="table_header"
                                style="width: 10px; color: black; border:none;background-color: transparent;float: none;">
                                <i class="fa fa-sort" style="width: 20px;"></i>
                        </button>
                    </span>
                    <span class=table_header style="width: 50px;">
                        <fmt:message key="data_unit"/>
                    </span>
                    <span class=table_header style="width: 100px;text-align: center;">
                        <fmt:message key="data_quantity"/>
                    </span>
                    <span class=table_header style="width: 100px;text-align: right;">
                        <fmt:message key="data_price"/>
                    </span>
                </div>
                <br>
                <hr>
                <div class=data_list>
                    <c:forEach var="item" items="${result}">
                        <span class=item style="width: 50px;text-align: center;">
                            ${item.id}
                        </span>
                        <span class=item style="width: 200px;text-align: center;">
                            <a href="${pageContext.request.contextPath}/controller?command=command.new_product&selectedProduct=${item.id}">
                                ${item.product.name}
                            </a>
                        </span>
                        <span class=item style="width: 50px;text-align: center;">
                            <fmt:message key="${item.product.unit}"/>
                        </span>
                        <span class=item style="width: 100px;text-align: center;">
                            ${item.quantity}
                        </span>
                        <span class=item style="width: 100px;text-align: right;">
                            ${item.product.price}
                        </span>
                        <br>
                        <hr>
                    </c:forEach>
                </div>
                <br>
                <%@ include file="/jsp/pagination.jspf" %>
            </div>
        </form>
    </body>
</html>