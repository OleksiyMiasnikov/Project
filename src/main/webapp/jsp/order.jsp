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
                TITLE
                <hr>
            </h2>
            <form action="serveOrder" method="post">
                Order id
                <input name="newId" value="${order.id}">
                <br>
                Date
                <input name="newDate" type="datetime-locale" value="${order.date}" />
                <br>
                Cashier
                <input name="newUser" value="${order.user.login}">
                <br>
                Amount
                <input name="newAmount" value="${order.totalAmount}">
                <br>

                <ul>
                    <c:forEach var="element" items="${orderDetails}">
                        <li>
                            ${element.id}
                            |
                            ${element.order.id}
                            |
                            ${element.product.name}
                            |
                            ${element.product.unit}
                            |
                            ${element.quantity}
                            |
                            ${element.price}
                            <br>
                        </li>
                    </c:forEach>
                </ul>

                  <hr>
                  <br>
                  <input type="submit" name="button" value="Ok">
               </div>
            </form>
        </div>
    </body>
</html>