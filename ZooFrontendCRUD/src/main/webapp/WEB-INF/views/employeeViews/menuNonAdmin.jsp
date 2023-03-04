<%-- 
    Document   : menuGuide
    Created on : 11 feb 2023, 0:37:14
    Author     : Agustín Pacheco

    Document   : menuNonAdmin
    Renamed on : 4 mar 2023, 11:16:33
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="p2.zoofrontendcrud.auxiliar.TYPE_ENUM"%>
<!DOCTYPE html>
<html>
    <head>
        <%
            HttpSession _session = request.getSession();

            if (_session.getAttribute("employeeUserName") == null || _session.getAttribute("employeeType") == null
                    || (_session.getAttribute("employeeType") != TYPE_ENUM.GUIDE
                    && _session.getAttribute("employeeType") != TYPE_ENUM.KEEPER) ) {
                out.print("<script>location.replace('/login');</script>");
            }
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu de Opciones</title>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    </head>
    <body>
        <h1>Hola <% out.print(session.getAttribute("employeeUserName")); %> bienvenido al sistema.</h1>
        <a class="simple-link" href='/login?cerrar=true'>Cerra Sesion</a>
        <a class="simple-link" 
           href='empleado/<%out.print(session.getAttribute("employeeUserName"));%>/consultardatos'>Consultar Datos</a>
    </body>
</html>
