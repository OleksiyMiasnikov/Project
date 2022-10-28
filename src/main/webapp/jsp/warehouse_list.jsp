<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/main.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css"/>
    </head>
<body>
        <%@ include file="/jsp/header.jspf" %>
        <script src=js/pagination.js></script>
            <div class="dataBox">
                <span style="font-size: 8px;">
                    [warehouse_list.jsp]
                </span>
                <p class="header_title">
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
                    </span>
                    <span class=table_header style="width: 200px;">
                        <fmt:message key="data_product_name"/>
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
                            <c:choose>
                                <c:when test="${employee.user.role.name == 'commodity expert'}">
                                    <a href="${pageContext.request.contextPath}/controller?command=command.new_product&selectedProduct=${item.id}">
                                        ${item.product.name}
                                    </a>
                                </c:when>
                                <c:otherwise>
                                     ${item.product.name}
                                </c:otherwise>
                            </c:choose>
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