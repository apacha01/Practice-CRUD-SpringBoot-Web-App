<%-- 
    Document   : error
    Created on : 14 feb 2023, 4:36:46
    Author     : Agustín Pacheco
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
    <body>
        <h1>Lo sentimos, ha ocurrido un error.</h1>
    <c:choose>
        <c:when test="${empty errorMsg}">
            <p>Error: desconocido</p>
        </c:when>
        <c:otherwise>
            <p>Error: ${errorMsg}</p>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${empty exception}">
        </c:when>
        <c:otherwise>
            <p>Exception: ${exception}</p>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${empty request}">
        </c:when>
        <c:otherwise>
            <p>Request Body: ${request}</p>
        </c:otherwise>
    </c:choose>
    <%
        out.print("Response status: " + response.getStatus() + "<br>");
    %>
</body>
</html>
