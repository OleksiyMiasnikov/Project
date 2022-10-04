<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <%@ include file="/jsp/header.jspf" %>
            <input id="pcs" value="${pcs}" hidden="hidden">
            <div class="dataBox">
                <span style="font-size: 8px;">
                    [new_order.jsp] ${operation}
                </span>
                <p style="text-align: center; font-size: 22px;font-weight: bold">
                    ***
                    <fmt:message key="new_order.new_${operation}"/>
                    ***
                </p>
                <hr>
                <span class="header_key">
                    <fmt:message key="new_order.${operation}_id"/>
                </span>
                <span class="header_value" >
                    ${order.id}
                </span>
                <br>
                <span class="header_key">
                    <fmt:message key="data_date"/>
                </span>
                <span class="header_value" >
                    ${order.date}
                </span>
                <br>
                <span class="header_key">
                    <fmt:message key="data_cashier"/>
                </span>
                <span class="header_value">
                    ${order.user.login}
                </span>
                <br>
                <span class="header_key">
                    <fmt:message key="data_total_amount"/>
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
                        placeholder='<fmt:message key="data_id"/>'
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
                        placeholder='<fmt:message key="data_product_name"/>'
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
                        step="0.001"
                        placeholder='<fmt:message key="data_quantity"/>'
                        default value="0"
                        min="0"
                        onchange="quantityUpdated();"
                        style="width: 100px;"
                        max=5>
                <input name="newUnit"
                        id="newUnit"
                        placeholder='<fmt:message key="data_unit"/>'
                        style="width: 70px;"
                        disabled>
                <input name="newPrice"
                        id="newPrice"
                        type="number"
                        placeholder='<fmt:message key="data_price"/>'
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
                <span class="table_header" style="width: 50px;">
                    <fmt:message key="data_id"/>
                </span>
                <span class="table_header" style="width: 200px;">
                    <fmt:message key="data_product_name"/>
                </span>
                <span class="table_header" style="width: 50px;">
                    <fmt:message key="data_unit"/>
                </span>
                <span class="table_header" style="width: 100px;">
                    <fmt:message key="data_quantity"/>
                </span>
                <span class="table_header" style="width: 100px;">
                    <fmt:message key="data_price"/>
                </span>
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
                        <span class="item" style="width: 100px;" type="number" step="0.001">
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
                    <%@ include file="/jsp/pagination.jspf" %>
                </c:if>
                <br>
                <div class="submit_button">
                    <input  name="command" value="command.new_order" hidden="hidden">
                    <input  name="direction" value="${direction}" hidden="hidden">
                    <button type="submit" name="button" value="Complete">
                            <fmt:message key="button_submit_complete"/>
                    </button>
                    <br>
                    <button style="margin-left: 20px;"
                        type="submit"
                        name="button"
                        value="Cancel">
                            <fmt:message key="button_submit_cancel"/>
                    </button>
                </div>
            </div>
        </form>
        <script src="${pageContext.request.contextPath}/js/new_order.js"></script>
    </body>
</html>
