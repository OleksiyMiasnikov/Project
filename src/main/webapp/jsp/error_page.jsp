<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
    <body>
        <%@ include file="/jsp/header.jspf" %>
            <div class="dataBox">
                <span style="font-size: 8px;">
                    [error_page.jsp]
                </span>
                <hr>
                <div style="text-align: center;">
                    <c:if test="${!empty error_message}">
                        ${error_message}
                    </c:if>
                    <c:if test="${!empty error_cause}">
                        ${error_cause}
                    </c:if>
                </div>
                <hr>
            </div>
        </form>

    </body>
</html>