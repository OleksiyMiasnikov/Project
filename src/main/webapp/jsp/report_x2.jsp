<!DOCTYPE html>
<html>
<body>
        <%@ include file="/jsp/header.jspf" %>
            <div class="dataBox">
                <%--
                <iframe src="${pageContext.request.contextPath}${pdf}" width="560px" height="780px"></iframe>
                --%>
                <object data="${pdf}"
                        type="application/pdf" width="100%" height="100%">
                  <p>Alternative text - include a link <a href="${pdf}">yourfile</a></p>
                </object>
            </div>
        </form>
    </body>
</html>