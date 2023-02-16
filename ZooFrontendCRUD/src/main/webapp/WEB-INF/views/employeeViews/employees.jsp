<%-- 
    Document   : employees
    Created on : 12 feb 2023, 1:42:54
    Author     : Agustín Pacheco
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="p2.zoofrontendcrud.auxiliar.TYPE_ENUM"%>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administracion de Empleados</title>
        <link rel="stylesheet" href="css/_reset.css"/>
        <link rel="stylesheet" href="css/button.css"/>
        <link rel="stylesheet" href="css/table.css"/>
        <link rel="stylesheet" href="css/style.css"/>
    </head>
</head>
<body>
    <%
        HttpSession _session = request.getSession();

        if (_session.getAttribute("employeeUserName") == null || _session.getAttribute("employeeType") == null
                || _session.getAttribute("employeeType") != TYPE_ENUM.ADMIN) {
            out.print("<script>location.replace('/login');</script>");
        }
    %>
    <h1>Empleados</h1>
    <h2>${errorMsg}</h2>
    <main class="clientes-container">
        <table class="table">
            <thead>
                <tr>
                    <th class="table__column--s">ID</th>
                    <th class="table__column--s">Ocupación</th>
                    <th class="table__column--s">Nombre</th>
                    <th class="table__column--s">Usuario</th>
                    <th class="table__column--s">Direccion</th>
                    <th class="table__column--s">Telefono</th>
                    <th class="table__column--s">Dia de alta</th>
                    <th class="table__column--s">A cargo de</th>
                    <th class="table__column--m table__align--right">
                        <a
                            href="/crear_empleado"
                            class="simple-button simple-button--add">
                            Nuevo Empleado
                        </a>
                    </th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="employee" items="${employees}">
                    <tr>
                        <td class="td">${employee.id}</td>
                        <td class="td">${employee.type}</td>
                        <td class="td">${employee.name}</td>
                        <td class="td">${employee.user_name}</td>
                        <td class="td">${employee.address}</td>
                        <td class="td">${employee.phone}</td>
                        <td class="td">${employee.first_day}</td>
                        <td class="td">
                            <c:choose>
                                <c:when test="${employee.type == TYPE_ENUM.KEEPER}">
                                    <button class="simple-button species-button"
                                            id="${employee.id}"
                                            name="get_employee_species">
                                        Especies
                                    </button>
                                </c:when>
                                <c:when test="${employee.type == TYPE_ENUM.GUIDE}">
                                    <button class="simple-button itineraries-button"
                                            id="${employee.id}"
                                            name="get_employee_itineraries">
                                        Itinerarios
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <p>-----</p>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="td">
                            <ul class="table__button-control">
                                <li>
                                    <a
                                        href="/editar_empleado/${employee.id}"
                                        class="simple-button simple-button--edit">
                                        Editar
                                    </a>
                                </li>
                                <li>
                                    <button class="simple-button simple-button--delete delete_employee"
                                            id="${employee.id}"
                                            name="delete_employee">
                                        Eliminar
                                    </button>
                                </li>
                            </ul>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div class="confirm_delete--container hidden" id="confirm_delete">
            <p class="confirm_delete--txt">¿Esta seguro que desea eliminar este empleado?</p>
            <div class="button-container">
                <form method="post" action="/error" id="delete_form">
                    <input type="submit" class="simple-button simple-button--delete" value="Eliminar">
                </form>
                <button class="simple-button gray-button" id="cancel_delete">
                    No Eliminar
                </button>
            </div>
        </div>
        <script>
            const btns = Array.from(document.getElementsByClassName('delete_employee'));
            const container = document.getElementById('confirm_delete');
            const btn_cancel = document.getElementById('cancel_delete');
            const delete_form = document.getElementById('delete_form');
            btns.forEach(function (btn) {
                btn.addEventListener('click', function () {
                    delete_form.action = "/eliminar_empleado/" + btn.id;
                    container.classList.remove('hidden');
                });
            });
            btn_cancel.addEventListener('click', function () {
                container.classList.add('hidden');
            });
        </script>
</body>
</html>
