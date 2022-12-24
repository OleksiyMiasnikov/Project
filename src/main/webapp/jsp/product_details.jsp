<!DOCTYPE html>
<html>
    <body>
        <%@ include file="/jsp/header.jspf" %>
        <script src=></script>
            <div class="dataBox">
                <span style="font-size: 8px;">
                    [product_details.jsp]
                </span>
                <p class="header_title">
                    <c:choose>
                        <c:when test="${result.id == 0}">
                            *** <fmt:message key="command.new_product"/> ***
                        </c:when>
                        <c:otherwise>
                            *** <fmt:message key="command.update_product"/> ***
                        </c:otherwise>
                    </c:choose>
                </p>
                <hr>
                <c:if test="${result.id != 0}">
                    <span class="header_key">
                        <fmt:message key="data_id"/>
                    </span>
                    <input class="header_value" name="newId" value = ${result.id}>
                    <br>
                </c:if>
                <span class="header_key">
                    <fmt:message key="data_product_name"/>
                </span>
                <input class="header_value" name="newName" value="${result.name}">
                <br>
                <span class="header_key">
                    <fmt:message key="data_unit"/>
                </span>
                <select class="header_value" name="newUnit" value="${result.unit}">
                <option value='${result.unit}'>
                    <fmt:message key="${result.unit}"/>
                </option>
                    <c:forEach var="item" items="${units }">
                        <option value='${item}'>
                            <fmt:message key="${item}"/>
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
            <%--
                <p>
                    <button class="submit_button" type="submit" name="command" value="command.create_product">
                        <fmt:message key="button_submit_ok"/>
                    </button>
                    <button class="submit_button" type="submit" name="command" value="command.list_of_products">
                        <fmt:message key="button_submit_cancel"/>
                    </button>
                </p>
             --%>
            </div>
        </form>
    </body>
</html>