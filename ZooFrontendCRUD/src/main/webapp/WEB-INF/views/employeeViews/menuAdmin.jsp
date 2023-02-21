<%-- 
    Document   : adminMenu
    Created on : 9 feb 2023, 19:17:10
    Author     : Agustín Pacheco
--%>

<%@page import="p2.zoofrontendcrud.auxiliar.TYPE_ENUM"%>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu de Opciones (Administrador)</title>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    </head>
    <body>
        <%
            HttpSession _session = request.getSession();
            
            if (_session.getAttribute("employeeUserName") == null || _session.getAttribute("employeeType") == null
            || _session.getAttribute("employeeType") != TYPE_ENUM.ADMIN) {
                out.print("<script>location.replace('/login');</script>");
            }
        %>
        <h1>Hola <% out.print(_session.getAttribute("employeeUserName")); %>, bienvenido al sistema.</h1>
        <a class="simple-link" href='/login?cerrar=true'>Cerra Sesion</a>
        <a class="simple-link" href="/empleados">Empleados</a>
        <a class="simple-link" href="/especies">Especies</a>
        <a class="simple-link" href="/zonas">Zonas</a>
        <a class="simple-link" href="/habitats">Habitats</a>
        <a class="simple-link" href="/itinerarios">Itinerarios</a>
    </body>
</html>
