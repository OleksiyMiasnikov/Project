<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <body>
        <%@ include file="header.jspf" %>
        <div style="margin-left:25%;padding:1px 16px;height:1000px;">
            <h2>
                Adding new user
                <hr>
            </h2>
            <form action="addUser" method="post">
                  Login <input name="login" required>
                  <br>
                  Password <input type="password" name="password" required>
                  <br>
                  Email <input name="email">
                  <br>
                  Role <input name="role" required>
                  <hr>
                  <select id="distribute_type"  class="textbox combo" name="distribute_type" >
                           <Опциональное значение = ""> Пожалуйста, выберите </ Опция>
                       <c:forEach items="${distribution }" var="diss">
                           <option value="${diss.DISTRIBUTIONTYPE }">${diss.DISTRIBUTIONTYPE }</option>
                       </c:forEach>
                   </select>
                  <hr>
                  <br>
                  <input type="submit" value="Create user">
                  <br>
                  <input type="submit" value="Cancel">
               </div>
            </form>
        </div>
    </body>
</html>