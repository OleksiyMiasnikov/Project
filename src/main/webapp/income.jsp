<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

    </head>
    <body>
        <c:if test="${sessionScope.Employee.user.role.name ne 'commodity expert'}">
            Not a commodity expert!!!
        </c:if>
        <script src="js/new_order.js"></script>
        <%@ include file="header.jspf" %>

        <div class="dataBox">
            <h2 class="id">
                INCIME [income.jsp]
                <hr>
            </h2>
            <form action="serveNewIncome" method="post">
                <div style="margin-left: 400px; margin-top: -50px;">
                    <input type="submit" name="button" value="Complete">
                    <input style="margin-left: 20px;"type="submit" name="button" value="Cancel">
                </div>
                <br>
                <br>
                <span class="header_key">
                    Order id
                </span>
                <span class="header_value" >
                    ${order.id}
                </span>
                <br>
                <span class="header_key">
                    Date
                </span>
                <span class="header_value" >
                    ${order.date}
                </span>
                <br>
                <span class="header_key">
                    Cashier
                </span>
                <span class="header_value">
                    ${order.user.login}
                </span>
                <br>
                <span class="header_key">
                    Total Amount
                </span>
                <input class="header_value"
                        name="total"
                        value="${order.totalAmount}"
                        onchange="newTotal.value = this.value"
                        style="background: transparent; border: none; text-align: left;"
                        disabled>
                <hr>
                    <select id="AllProducts" hidden="hidden">
                        <c:forEach var="item" items="${products }">
                            <option value="${item.id }|${item.name }|${item.price }|${item.unit.getLabelUa()}|1000000">
                            </option>
                        </c:forEach>
                    </select>

                    <input  list="idList"
                            name="newProductId"
                            id="newProductId"
                            onchange="idUpdated();"
                            style="width: 100px;">
                    <datalist id="idList">
                        <c:forEach var="item" items="${products }">
                            <option value="${item.id }">
                                ${item.id }
                            </option>
                        </c:forEach>
                    </datalist>
                    <input  class="id"
                            list="productList"
                            name="newProduct"
                            id="newProduct"
                            onchange="productUpdated();"
                            style="width: 250px;text-align: center;">
                    <datalist id="productList">
                        <c:forEach var="item" items="${products }">
                            <option value="${item.name }">
                            ${item.name }
                            </option>
                        </c:forEach>
                    </datalist>
                    <br>
                    <input name="newQuantity"
                            id="newQuantity"
                            type="number"
                            default value="0"
                            min="0"
                            onchange="updateAmount();"
                            style="width: 100px;">
                    <input name="newUnit"
                            id="newUnit"
                            style="width: 70px;"
                            disabled>
                    <input name="newPrice"
                            id="newPrice"
                            type="number"
                            step="0.01"
                            onchange="updateAmount();"
                            style="width: 100px;">
                    <input name="newAmount"
                            id="newAmount"
                            style="width: 150px;"
                            disabled>
                <br>
                <input type="submit" name="button" value="Save">
                <br>
                <hr>
                <span class="table_header" style="width: 50px;">Id</span>
                <span class="table_header" style="width: 200px;">Product name</span>
                <span class="table_header" style="width: 50px;">Unit</span>
                <span class="table_header" style="width: 100px;">Quantity</span>
                <span class="table_header" style="width: 100px;">Price</span>
                <br>
                <hr>
                <c:forEach var="element" items="${orderDetails}">
                    <span id="checkSpan" class="item" style="width: 50px;" hidden="hidden">
                        ${element.id}
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
               </div>
            </form>
        </div>
    </body>
</html>