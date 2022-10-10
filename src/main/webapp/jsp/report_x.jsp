<!DOCTYPE html>
<html>
<body>
        <%@ include file="/jsp/header.jspf" %>
            <div class="dataBox">
                ${pdf}
                <jsp:include page="/ReportPDF" flush="true" />
            </div>
        </form>
    </body>
</html>