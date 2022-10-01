<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
   <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
   </head>
    <body>
        <%@ include file="/jsp/header.jspf" %>
        <div class="dataBox">
            <h2>
                TITLE [order_details.jsp]
                <hr>
            </h2>
            <form action="serveOrderDetails" method="post">
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
                  <input type="submit" name="button" value="Cancel">
               </div>
            </form>
        </div>
    </body>
</html>