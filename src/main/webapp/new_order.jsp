<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <%@ include file="header.jspf" %>
            <div class="dataBox">
                <span style="font-size: 8px;">
                    [new_order.jsp]
                </span>
                <p style="text-align: center; font-size: 22px;font-weight: bold">
                    *** New ${operation} ***
                </p>
                <hr>
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
                    <c:forEach var="item" items="${warehouse }">
                        <option value="${item.product.id }|${item.product.name }|${item.product.price }|${item.product.unit.labelUa}|${item.quantity } ">
                        </option>
                    </c:forEach>
                </select>

                <input  list="idList"
                        name="newProductId"
                        id="newProductId"
                        onchange="idUpdated();"
                        placeholder="id"
                        style="width: 100px;">
                <datalist id="idList">
                    <c:forEach var="item" items="${warehouse }">
                        <option value="${item.product.id }">
                            ${item.product.id }
                        </option>
                    </c:forEach>
                </datalist>
                <input  class="id"
                        list="productList"
                        name="newProduct"
                        id="newProduct"
                        placeholder="product name"
                        onchange="productUpdated();"
                        style="width: 250px;text-align: center;">
                <datalist id="productList">
                    <c:forEach var="item" items="${warehouse }">
                        <option value="${item.product.name }">
                        ${item.product.name }
                        </option>
                    </c:forEach>
                </datalist>
                <input name="newQuantity"
                        id="newQuantity"
                        type="number"
                        placeholder="quantity"
                        default value="0"
                        min="0"
                        onchange="updateAmount();"
                        style="width: 100px;"
                        max=5>
                <input name="newUnit"
                        id="newUnit"
                        placeholder="unit"
                        style="width: 70px;"
                        disabled>
                <input name="newPrice"
                        id="newPrice"
                        type="number"
                        placeholder="price"
                        step="0.01"
                        onchange="updateAmount();"
                        style="width: 100px;">
                <input name="newAmount"
                        id="newAmount"
                        style="width: 150px;"
                        disabled>
                <button type="submit" name="button" value="Save">
                    Save
                </button>
                <br>
                <hr>
                <span class="table_header" style="width: 50px;">Id</span>
                <span class="table_header" style="width: 200px;">Product name</span>
                <span class="table_header" style="width: 50px;">Unit</span>
                <span class="table_header" style="width: 100px;">Quantity</span>
                <span class="table_header" style="width: 100px;">Price</span>
                <br>
                <hr>
                <div class=data_list style="height: 200px;">
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
                            ${element.product.unit.labelUa}
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
                <c:if test="${pages_total > 1}">
                    <%@ include file="pagination.jspf" %>
                </c:if>
                <br>
                <div class="submit_button">
                    <input  name="command" value="New order" hidden="hidden">
                    <input  name="direction" value="${direction}" hidden="hidden">
                    <button type="submit" name="button" value="Complete">
                        Complete
                    </button>
                    <br>
                    <button style="margin-left: 20px;" type="submit" name="button" value="Cancel">
                        Cancel
                    </button>
                </div>
            </div>
        </form>
        <script src="js/new_order.js"></script>
    </body>
</html>
