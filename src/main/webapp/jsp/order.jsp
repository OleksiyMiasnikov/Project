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
            <div class="dataBox">
                <input id="userRole" value="${employee.user.role.name}" hidden="hidden">
                <span style="font-size: 8px;">
                    [order.jsp]
                </span>
                <p style="text-align: center; font-size: 22px;font-weight: bold">
                    ***
                    <fmt:message key="order_jsp.${operation}_details"/>
                    ***
                </p>
                <hr>
                <span class="header_key">
                    <fmt:message key="order_jsp.${operation}_id"/>
                </span>
                <span class="header_value">${order.id}</span>
                <br>
                <input name="order_id" value="${order.id}" hidden="hidden">
                <span class="header_key">
                    <fmt:message key="data_date"/>
                </span>
                <span class="header_value">${order.date}</span>
                <br>
                <span class="header_key">
                    <fmt:message key="data_cashier"/>
                </span>
                <span class="header_value">${order.user.login}</span>
                <br>
                <span class="header_key">
                    <fmt:message key="data_total_amount"/>
                </span>
                <span class="header_value">${order.totalAmount}</span>
                <hr>
                <div class="table_header">
                    <button type="submit"
                            id="delete_button"
                            name="command"
                            value="command.delete_ordered_product"
                            class="table_header"
                            style="width: 40px; color: black;"
                            hidden="hidden">
                        <i class="fa-solid fa-trash-can"></i>
                    </button>
                    <span class="table_header" style="width: 40px;">
                        <fmt:message key="data_id"/>
                    </span>
                    <span class="table_header" style="width: 180px;">
                        <fmt:message key="data_product_name"/>
                    </span>
                    <span class="table_header" style="width: 60px;">
                        <fmt:message key="data_unit"/>
                    </span>
                    <span class="table_header" style="width: 90px;">
                        <fmt:message key="data_quantity"/>
                    </span>
                    <span class="table_header" style="width: 90px;">
                        <fmt:message key="data_price"/>
                    </span>
                </div>
                <br>
                <hr>
                    <c:forEach var="element" items="${orderDetails}">
                            <span id="checkSpan" class="item" style="width: 40px;" hidden="hidden">
                                <input  type="checkbox"
                                        style="width: 40px; text-align:center;"
                                        class="center-block"
                                        name="products"
                                        id="myCheck"
                                        value="${element.id}"
                                        >
                            </span>
                            <span class="item" style="width: 40px;">
                                ${element.id}
                            </span>
                            <span class="item" style="width: 180px;">
                                ${element.product.name}
                            </span>
                            <span class="item" style="width: 60px;">
                                <fmt:message key="${element.product.unit}"/>
                            </span>
                            <span class="item" style="width: 90px;">
                                ${element.quantity}
                            </span>
                            <span class="item" style="width: 90px;">
                                ${element.price}
                            </span>
                            <br>
                            <hr>
                    </c:forEach>
                <br>
                <button type="submit" name="command" value='<fmt:message key="${operation}s"/>'>
                    <fmt:message key="button_submit_ok"/>
                </button>
               </div>
            </div>
        </form>
        <script src="${pageContext.request.contextPath}/js/order.js"></script>
    </body>
</html>