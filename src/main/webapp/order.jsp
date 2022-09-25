<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/main.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css"/>
    </head>
    <body>
        <form action="controller" method="post">
           <div class="sideBar">
                [order.jsp]
                <br>
                <div class="employee">
                    ${Employee.user.login} (${Employee.user.role.name})
                    <input id="userRole" value="${Employee.user.role.name}" hidden="hidden">
                </div>
                <br>
                <br>
                <c:forEach items="${Employee.menuItems}" var="item">
                    <input type="submit" value="${item}" id="${item}" name="command">
                    <br>
                </c:forEach>
                <br>
                <br>
                <br>
                <br>
                <input type="submit" value="Log out" name="command">
           </div>

            <div class="dataBox">
                <h2>
                    Order details [order.jsp]
                    <hr>
                </h2>
                    <span class="header_key">Order id</span>
                    <span class="header_value">${order.id}</span>
                    <br>
                    <input name="order_id" value="${order.id}" hidden="hidden">
                    <span class="header_key">Date</span>
                    <span class="header_value">${order.date}</span>
                    <br>
                    <span class="header_key">Cashier</span>
                    <span class="header_value">${order.user.login}</span>
                    <br>
                    <span class="header_key">Amount</span>
                    <span class="header_value">${order.totalAmount}</span>
                    <hr>
                    <div class="table_header">
                        <button type="submit"
                                id="delete_button"
                                name="command"
                                value="Delete ordered product"
                                class="table_header"
                                style="width: 50px; color: black;"
                                hidden="hidden">
                            <i class="fa-solid fa-trash-can"></i>
                        </button>
                        <span class="table_header" style="width: 50px;">Id</span>
                        <span class="table_header" style="width: 200px;">Product name</span>
                        <span class="table_header" style="width: 50px;">Unit</span>
                        <span class="table_header" style="width: 100px;">Quantity</span>
                        <span class="table_header" style="width: 100px;">Price</span>
                    </div>
                    <br>
                    <hr>
                        <c:forEach var="element" items="${orderDetails}">
                                <span id="checkSpan" class="item" style="width: 50px;" hidden="hidden">
                                    <input  type="checkbox"
                                            class="center-block"
                                            name="products"
                                            id="myCheck"
                                            value="${element.id}"
                                            >
                                </span>
                                <span class="item" style="width: 50px;">
                                    ${element.id}
                                </span>
                                <span class="item" style="width: 200px;">
                                    ${element.product.name}
                                </span>
                                <span class="item" style="width: 50px;">
                                    ${element.product.unit}
                                </span>
                                <span class="item" style="width: 100px;">
                                    ${element.quantity}
                                </span>
                                <span class="item" style="width: 100px;">
                                    ${element.price}
                                </span>
                                <br>
                                <hr>
                        </c:forEach>
                    <br>
                    <button type="submit" name="command" value="Orders">Ok</button>
                   </div>
            </div>
        </form>
        <script src="js/order.js"></script>
    </body>
</html>