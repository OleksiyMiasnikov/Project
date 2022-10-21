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
                <span class="header_value">${from_address_mail}</span>
                <br>
                <span class="header_key">
                    <fmt:message key="send_report.jsp_to"/>
                </span>
                <input style="text-align: left;" size=25 name="to_address_mail" value="${to_address_mail}">
                <br>
                <span class="header_key">
                    <fmt:message key="send_report.jsp_subject"/>
                </span>
                <input style="width=200px; text-align: left;" size=25 name="subject_mail" value="${subject_mail}">
                <br>
                <span class="header_key">
                    <fmt:message key="send_report.jsp_attachment"/>
                </span>
                <span class="header_value">${pdf.substring(pdf.lastIndexOf('/') + 1, pdf.length())}</span>
                <br>
                <hr>
                <label for="body_mail" class="header_key" style="vertical-align: top;"><fmt:message key="send_report.jsp_body"/></label>
                <textarea name="body_mail" id="body_mail" cols="40" rows="5"></textarea>
                <br>
            </div>
        </form>
    </body>
</html>