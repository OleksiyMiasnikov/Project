<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css"/>
   </head>
    <body>
        <%@ include file="header.jspf" %>

        <div style="margin-left:25%;padding:1px 16px;height:1000px;">
            <h2>
                Hello!!!
                <br>
                ${sessionScope.menuTitle}
            </h2>
            <style type="text/css">
             ul {
              list-style-type: none;
             }
            </style>
             <script type="text/javascript">
                function setAttributeRadio() {
                   //alert("Radio checked");
                   // $.post(document.URL+'?qqq=ajax');
                   //console.log(document.getElementById("radio"));
                    //console.log(document.URL+'?qqq=ajax');
                    //<%session.setAttribute("qqq", "dssadf");%>
                }
            </script>

                <ul>

                    <c:forEach var="user" items="${result}">
                        <li>
                            <i class="fa fa-trash" aria-hidden="true"></i>
                            --
                            <input type="radio" id="radio" value="${user.login}" name="users" onclick="setAttributeRadio()"/>
                            ${user.login}
                            :
                            ${user.role.name}
                            --
                            <i class="fa fa-pencil" aria-hidden="true"></i>
                        </li>
                    </c:forEach>
                </ul>


        </div>

    </body>
</html>