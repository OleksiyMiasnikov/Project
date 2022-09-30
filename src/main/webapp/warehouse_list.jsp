<!DOCTYPE html>
<html>
<body>
        <%@ include file="header.jspf" %>
        <script src=js/pagination.js></script>
            <div class="dataBox">
                <span style="font-size: 8px;">
                    [warehouse_list.jsp]
                </span>
                <p style="text-align: center; font-size: 22px;font-weight: bold">
                    *** Remains in warehouse ***
                </p>
                <hr>
                <span class="header_key" style="width: 170px;">
                    Total quantity :
                </span>
                <span>
                    ${total_quantity}
                </span>
                <br>
                <span class="header_key" style="width: 170px;">
                    Total amount :
                </span>
                <span>
                    ${total_amount}
                </span>
                <hr>
                <div class=table_header>
                    <span class=table_header style="width: 50px;">
                        Id
                        <button type="submit"
                                name="command"
                                id="delete_button"
                                value=""
                                class="table_header"
                                style="width: 10px; color: black; border:none;background-color: transparent;float: none;">
                                <i class="fa fa-sort" style="width: 20px;"></i>
                        </button>
                    </span>
                    <span class=table_header style="width: 200px;">
                        Product name
                        <button type="submit"
                                name="command"
                                id="delete_button"
                                value=""
                                class="table_header"
                                style="width: 10px; color: black; border:none;background-color: transparent;float: none;">
                                <i class="fa fa-sort" style="width: 20px;"></i>
                        </button>
                    </span>
                    <span class=table_header style="width: 50px;">
                        Unit
                    </span>
                    <span class=table_header style="width: 100px;">
                        Quantity
                    </span>
                    <span class=table_header style="width: 100px;text-align: right;">
                        Price
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
                            <a href="controller?command=New product&selectedProduct=${item.id}">
                                ${item.product.name}
                            </a>
                        </span>
                        <span class=item style="width: 50px;text-align: center;">
                            ${item.product.unit.labelUa}
                        </span>
                        <span class=item style="width: 100px;text-align: right;">
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
                <%@ include file="pagination.jspf" %>
            </div>
        </form>
    </body>
</html>