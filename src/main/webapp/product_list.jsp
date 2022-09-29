<!DOCTYPE html>
<html>
<body>
        <%@ include file="header.jspf" %>
        <script src=js/pagination.js></script>
            <div class="dataBox">
                <span style="font-size: 8px;">
                    [ProductListServlet: /ProductListFragment]
                </span>
                <p style="text-align: center; font-size: 22px;font-weight: bold">
                    *** List of products ***
                </p>
                <hr>
                <div class=table_header>
                    <span class=table_header style="width: 50px;">
                        Id
                    </span>
                    <span class=table_header style="width: 250px;">
                        Product name
                    </span>
                    <span class=table_header style="width: 50px;">
                        Unit
                    </span>
                    <span class=table_header style="width: 150px;text-align: right;">
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
                        <span class=item style="width: 250px;text-align: center;">
                            <a href="controller?command=New product&selectedProduct=${item.id}">
                                ${item.name}
                            </a>
                        </span>
                        <span class=item style="width: 50px;text-align: center;">
                            ${item.unit}
                        </span>
                        <span class=item style="width: 150px;text-align: right;">
                            ${item.price}
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