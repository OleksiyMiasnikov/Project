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
                Adding new user
                <hr>
            </h2>
            <form action="serveOrder" method="post">
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
                  <input type="submit" name="button" value="Ok">
                  <input type="submit" name="button" value="Cancel">
               </div>
            </form>
        </div>
    </body>
</html>