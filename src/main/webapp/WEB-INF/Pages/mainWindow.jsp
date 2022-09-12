<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <body>
        <%@ include file="header.jspf" %>

        <div style="margin-left:25%;padding:1px 16px;height:1000px;">
            <h2>
                Hello!!!
                <br>
                ${sessionScope.menuTitle}
            </h2>
        </div>
    </body>
</html>