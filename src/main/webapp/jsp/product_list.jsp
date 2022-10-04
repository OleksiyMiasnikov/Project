<!DOCTYPE html>
<html>
<body>
        <%@ include file="/jsp/header.jspf" %>
        <script src=js/pagination.js></script>
            <div class="dataBox">
                <span style="font-size: 8px;">
                    [product_list.jsp]
                </span>
                <p style="text-align: center; font-size: 22px;font-weight: bold">
                    *** <fmt:message key="product_list_jst.list_of_products"/> ***
                </p>
                <hr>
                <div class=table_header>
                    <span class=table_header style="width: 50px;">
                        <fmt:message key="data_id"/>
                    </span>
                    <span class=table_header style="width: 250px;">
                        <fmt:message key="data_product_name"/>
                    </span>
                    <span class=table_header style="width: 50px;">
                        <fmt:message key="data_unit"/>
                    </span>
                    <span class=table_header style="width: 150px;text-align: right;">
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
                        <span class=item style="width: 250px;text-align: center;">
                            <a href="${pageContext.request.contextPath}/controller?command=command.new_product&selectedProduct=${item.id}">
                                ${item.name}
                            </a>
                        </span>
                        <span class=item style="width: 50px;text-align: center;">
                            <fmt:message key="${item.unit}"/>
                        </span>
                        <span class=item style="width: 150px;text-align: right;">
                            ${item.price}
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
    </body>
</html>