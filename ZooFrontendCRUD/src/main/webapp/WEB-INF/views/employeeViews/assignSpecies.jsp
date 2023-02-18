<%-- 
    Document   : assignSpecies
    Created on : 18 feb 2023, 14:49:22
    Author     : Agustín Pacheco
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@page import="p2.zoofrontendcrud.auxiliar.TYPE_ENUM"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Asignar Especies</title>
        <link rel="stylesheet" href="css/form.css"/>
        <link rel="stylesheet" href="css/style.css"/>
    </head>
    <body>
        <%
            HttpSession _session = request.getSession();

            if (_session.getAttribute("employeeUserName") == null || _session.getAttribute("employeeType") == null
                    || _session.getAttribute("employeeType") != TYPE_ENUM.ADMIN) {
                out.print("<script>location.replace('/login');</script>");
            }
        %>
        <h1>Las especies de ${e.name} están tildadas. Tilde las que desee agregar o destilde las que desee quitarle:</h1>
    </body>
</html>
