<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>

        <%@ include file="header.jspf" %>
        <div class="dataBox">
            <h2>
                Order details
                <hr>
            </h2>
            <form action="serveOrder" method="post">
                <p>
                    <span class="header_key">Order id</span>
                    <span class="header_value">${order.id}</span>
                </p>
                <p>
                    <span class="header_key">Date</span>
                    <span class="header_value">${order.date}</span>
                </p>
                <p>
                    <span class="header_key">Cashier</span>
                    <span class="header_value">${order.user.login}</span>
                </p>
                <p>
                    <span class="header_key">Amount</span>
                    <span class="header_value">${order.totalAmount}</span>
                </p>
                <br>
                <hr>
                <div class="table_header">
                    <span class="item" style="width: 50px;">
                        <i class="fa-solid fa-trash-can"></i>
                    </span>
                    <span class="item" style="width: 50px;">Id</span>
                    <span class="item" style="width: 70px;">Order Id</span>
                    <span class="item" style="width: 200px;">Product name</span>
                    <span class="item" style="width: 50px;">Unit</span>
                    <span class="item" style="width: 100px;">Quantity</span>
                    <span class="item" style="width: 100px;">Price</span>
                </div>
                <br>
                <hr>
                    <c:forEach var="element" items="${orderDetails}">
                            <span class="item" style="width: 50px;">
                                <input  type="checkbox"
                                        name="products"
                                        id="myCheck"
                                        value="${element.id}">
                            </span>
                            <span class="item" style="width: 50px;">
                                ${element.id}
                            </span>
                            <span class="item" style="width: 70px;">
                                ${element.order.id}
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
                <input type="submit" name="button" value="Ok">
               </div>
            </form>
        </div>
    </body>
</html>