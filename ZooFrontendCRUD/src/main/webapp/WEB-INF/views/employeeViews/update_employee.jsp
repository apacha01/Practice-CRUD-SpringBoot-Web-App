<%-- 
    Document   : edit_employee
    Created on : 15 feb 2023, 9:09:59
    Author     : Agustín Pacheco
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@page import="p2.zoofrontendcrud.auxiliar.TYPE_ENUM"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Empleado</title>
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
        <h1>Edite los datos del empleado que desee actualizar</h1>
        <form method="post" action="/editar_empleado/${employee.id}" class="create_form">
            <select class="input_form" name="type">
                <c:forEach var="type" items="${TYPE_ENUM.values()}">
                    <option>${type.name}</option>
                </c:forEach>
            </select>
            <input class="input_form" type="text" name="userName" placeholder="Nombre de Usuario" value="${employee.user_name}" required>
            <input class="input_form" type="password" name="password" placeholder="Contraseña" value="${employee.password}" required>
            <input class="input_form" type="text" name="name" placeholder="Nombre" value="${employee.name}" required>
            <input class="input_form" type="text" name="address" placeholder="Direccion" value="${employee.address}" required>
            <input class="input_form" type="number" name="phone" placeholder="Telefono" value="${employee.phone}" required>
            <input class="input_form" type="date" name="firstDay" placeholder="Dia de alta" value="${employee.first_day}" required>
            <input class="button_form" type="submit">
        </form>
    </body>
</html>
