<!DOCTYPE html>
<html>
<body>
        <%@ include file="/jsp/header.jspf" %>
            <div class="dataBox">
                <span style="font-size: 8px;">
                    [send_report.jsp]
                </span>
                <p class="header_title">
                    ***
                    <fmt:message key="send_report.jsp_send_report"/>
                    ***
                </p>
                <hr>
                <span class="header_key">
                    <fmt:message key="send_report.jsp_from"/>
                </span>
                <span class="header_value">${from_address}</span>
                <br>
                <span class="header_key">
                    <fmt:message key="send_report.jsp_to"/>
                </span>

                <input class="header_value" name="order_id" value="${to_address}">
                <br>
                <hr>
            </div>
        </form>
    </body>
</html>