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
                product details
                <hr>
            </h2>
            <form action="serveProduct" method="post">
                  Name
                  <input name="newName" value="${product.name}">
                  <input type="hidden" name="id" value="${product.id}">
                  <br>
                  Unit
                  <input name="newUnit" value="${product.unit}">
                  <br>
                  <select id="enumUnit">
                     <option value="øò">øò</option>
                     <option value="êã">êã</option>
                  </select>
                  <br>
                  Price
                  <input name="newPrice" value="${product.price}" pattern="^[0-9]+\.?[0-9]{0,2}$">
                  <hr>
                  <br>
                  <input type="submit" name="button" value="Ok">
                  <input type="submit" name="button" value="Cancel">
               </div>
            </form>
        </div>
    </body>
</html>