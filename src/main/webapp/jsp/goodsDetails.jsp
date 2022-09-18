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
                Goods details
                <hr>
            </h2>
            <form action="serveGoods" method="post">
                  Name
                  <input name="newName" value="${goods.name}">
                  <input type="hidden" name="id" value="${goods.id}">
                  <br>
                  Unit
                  <input name="newUnit" value="${goods.unit}">
                  <br>
                  Price
                  <input name="newPrice" value="${goods.price}" pattern="^[0-9]+\.?[0-9]{0,2}$">
                  <hr>
                  <br>
                  <input type="submit" name="button" value="Ok">
                  <input type="submit" name="button" value="Cancel">
               </div>
            </form>
        </div>
    </body>
</html>