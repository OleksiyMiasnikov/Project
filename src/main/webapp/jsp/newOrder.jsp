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
                NEW ORDER
                <hr>
            </h2>
            <form action="serveNewOrder" method="post">
                Order id :
                <input name="id" value="${order.id}" onkeyup="newId.value = this.value" disabled>
                <input name="newId" value="${order.id}" hidden="hidden">
                <br>
                Date :
                <input name="date" value="${order.date}" onkeyup="newDate.value = this.value" disabled>
                <input name="newDate" value="${order.date}" hidden="hidden">
                <br>
                Cashier :
                <input name="user" value="${order.user.login}" onkeyup="newUser.value = this.value" disabled>
                <input name="newUser" value="${order.user.login}" hidden="hidden">
                <br>
                <hr>
                    <select name="newGoods" onchange="newUnit1.selectedIndex=this.selectedIndex;
                                                      newUnit.value=newUnit1.value;
                                                      newPrice1.selectedIndex=this.selectedIndex;
                                                      newPrice.value=newPrice1.value;
                                                      newGoodsId.selectedIndex=this.selectedIndex;
                                                      newAmount.value = newQuantity.value*newPrice.value">
                        <option> --- </option>
                        <c:forEach var="item" items="${goods }">
                            <option value="${item.name }">
                                ${item.name }
                            </option>
                        </c:forEach>
                    </select>
                    <select name="newGoodsId" hidden="hidden">
                        <option> --- </option>
                        <c:forEach var="item" items="${goods }">
                            <option value="${item.id }">
                                ${item.id }
                            </option>
                        </c:forEach>
                    </select>
                    <select name="newPrice1" hidden="hidden">
                        <option> --- </option>
                        <c:forEach var="item" items="${goods }">
                            <option value="${item.price }">
                                ${item.price }
                            </option>
                        </c:forEach>
                    </select>
                    <select name="newUnit1" hidden="hidden">
                        <option> --- </option>
                        <c:forEach var="item" items="${goods }">
                            <option value="${item.unit }">
                                ${item.unit }
                            </option>
                        </c:forEach>
                    </select>
                    |
                    <input name="newQuantity" type="number" onkeyup="newAmount.value = this.value*newPrice.value">
                    |
                    <input name="newUnit" disabled>
                    |
                    <input name="newPrice" type="number" step="0.01" onkeyup="newAmount.value = this.value*newQuantity.value">
                    |
                    <input name="newAmount" id="newAmount" disabled>
                    |
                    <input type="submit" name="button" value="Save">
                <br>
                <hr>
                Total Amount
                <input name="total" value="${order.totalAmount}" onkeyup="newTotal.value = this.value" disabled>
                <br>
                <ul>
                    <c:forEach var="element" items="${orderDetails}">
                        <li>
                            ${element.id}
                            |
                            ${element.order.id}
                            |
                            ${element.goods.name}
                            |
                            ${element.goods.unit}
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
                <input type="submit" name="button" value="Complete">
                <input type="submit" name="button" value="Cancel">
               </div>
            </form>
        </div>
    </body>
</html>