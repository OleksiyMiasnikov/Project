<!DOCTYPE html>
<html>
    <body>
        <%@ include file="header.jspf" %>
        <script src=></script>
            <div class="dataBox">
                <span style="font-size: 8px;">
                    [product_details.jsp]
                </span>
                <p style="text-align: center; font-size: 22px;font-weight: bold">
                    <c:choose>
                        <c:when test="${result.id == 0}">
                            *** New product ***
                        </c:when>
                        <c:otherwise>
                            *** Update product ***
                        </c:otherwise>
                    </c:choose>
                </p>
                <hr>
                <c:if test="${result.id != 0}">
                    <span class="header_key">
                        Id
                    </span>
                    <input class="header_value" name="newId" value = ${result.id}>
                    <br>
                </c:if>
                <span class="header_key">
                    Product name
                </span>
                <input class="header_value" name="newName" value="${result.name}">
                <br>
                <span class="header_key">
                    Unit
                </span>
                <select class="header_value" name="newUnit" value="${result.unit.labelUa}">
                <option>
                    ${result.unit.labelUa}
                </option>
                    <c:forEach var="item" items="${units }">
                        <option value="${item.labelUa }">
                            ${item.labelUa }
                        </option>
                    </c:forEach>
                </select>
                <br>
                <span class="header_key">
                    Price
                </span>
                <input class="header_value" name="newPrice" value=${result.price} pattern="^[0-9]+\.?[0-9]{0,2}$">
                <br>
                <hr>
                <p>
                    <button class="submit_button" type="submit" name="command" value="Create product">
                        Ok
                    </button>
                    <button class="submit_button" type="submit" name="command" value="List of products">
                        Cancel
                    </button>
                </p>
            </div>
        </form>
    </body>
</html>