<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
   <head>
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" href="css/login.css">
      <link rel="stylesheet" href="css/locale.css">
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css"/>
      <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato"> <%-- Lekton | Metrophobic | Oxanium | Share Tech | Text Me One --%>
      <title>
         <fmt:message key="index_jsp_title"/>
      </title>
   </head>

   <body>
      <%@ include file="/jsp/locale.jspf" %>
      <script src="js/login.js"></script>
      <div class="container">
         <div class="wrapper">
            <div class="title"><span><fmt:message key="index_jsp.form.title"/></span></div>
            <form action="controller" method="post">
               <input name="command" value="authorization" hidden="hidden">
               <div class="row">
                  <i class="fas fa-user"></i>
                  <input name="login" placeholder="<fmt:message key='data_login'/>">
               </div>
               <div class="row">
                  <i class="fas fa-lock"></i>
                  <input type="password" name="password" placeholder="<fmt:message key='data_password'/>" id="pass" required>
                  <i class="fas fa-eye-slash" id="eye" onclick="changingVisiabilityOfPassword()"></i>
               </div>
               <p class="error">
                    <c:if test="${!empty incorrectUser}">
                        <fmt:message key="${incorrectUser}"/>
                    </c:if>
               </p>
               <div class="row button">
                  <input type="submit" value="<fmt:message key='data_login'/>">
               </div>
            </form>
         </div>
      </div>
   </body>
</html>
