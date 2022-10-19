<!DOCTYPE html>
<html>
<body>
        <%@ include file="/jsp/header.jspf" %>
            <div class="dataBox">
                <iframe src="${pageContext.request.contextPath}${pdf}" width="560px" height="780px"></iframe>
            </div>
        </form>
    </body>
</html>