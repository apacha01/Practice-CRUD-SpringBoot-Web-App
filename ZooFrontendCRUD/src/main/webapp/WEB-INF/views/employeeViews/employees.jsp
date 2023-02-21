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
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/_reset.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/table.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/button.css"/>
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
        <h1>Empleados</h1>
        <h2>${errorMsg}</h2>
        <main class="employees-container">
            <table class="table">
                <thead>
                    <tr>
                        <th class="table__column--es">ID</th>
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
                            <td class="td">${employee.type.name}</td>
                            <td class="td">${employee.name}</td>
                            <td class="td">${employee.user_name}</td>
                            <td class="td">${employee.address}</td>
                            <td class="td">${employee.phone}</td>
                            <td class="td">${formatedFirstDay[employee.id]}</td>
                            <td class="td">
                                <c:choose>
                                    <c:when test="${employee.type == TYPE_ENUM.KEEPER}">
                                        <button class="simple-button species-button"
                                                id="show-${employee.id}"
                                                name="show_employee_panel">
                                            Especies
                                        </button>
                                        <div class="species-container hidden" 
                                             id="employeePanel-${employee.id}"
                                             name="employee_panel">
                                            <h2 class="table-title">Especies a cargo de ${employee.name}</h2>
                                            <table class="mini-table">
                                                <thead>
                                                    <tr>
                                                        <th class="table__column--s">ID</th>
                                                        <th class="table__column--s">Nombre</th>
                                                        <th class="table__column--s">Zona</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="sp" items="${keepersSpecies[employee.id]}">
                                                        <tr>
                                                            <td class="td">${sp.id}</th>
                                                            <td class="td">${sp.name}</th>
                                                            <td class="td">${sp.zone.name}</th>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                            <button class="simple-button simple-button--delete"
                                                    id="close-${employee.id}"
                                                    name="close_panel">
                                                Cerrar
                                            </button>
                                        </div>
                                    </c:when>
                                    <c:when test="${employee.type == TYPE_ENUM.GUIDE}">
                                        <button class="simple-button itineraries-button"
                                                id="show-${employee.id}"
                                                name="show_employee_panel">
                                            Itinerarios
                                        </button>
                                        <div class="itineraries-container hidden" 
                                             id="employeePanel-${employee.id}"
                                             name="employee_panel">
                                            <h2 class="table-title">Itinerarios a cargo de ${employee.name}</h2>
                                            <table class="mini-table">
                                                <thead>
                                                    <tr>
                                                        <th class="table__column--s">ID</th>
                                                        <th class="table__column--s">Codigo</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="iti" items="${guidesItineraries[employee.id]}">
                                                        <tr>
                                                            <td class="td">${iti.id}</th>
                                                            <td class="td">${iti.code}</th>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                            <button class="simple-button simple-button--delete"
                                                    id="close-${employee.id}"
                                                    name="close_panel">
                                                Cerrar
                                            </button>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <p style="text-align: center;">-</p>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="td">
                                <ul class="table__button-control">
                                    <div class="UDops-button-container">
                                        <li>
                                            <a
                                                href="/editar_empleado/${employee.id}"
                                                class="simple-button simple-button--edit">
                                                Editar
                                            </a>
                                        </li>
                                        <li>
                                            <button class="simple-button simple-button--delete"
                                                    id="delete-${employee.id}"
                                                    name="delete_employee">
                                                Eliminar
                                            </button>
                                        </li>
                                    </div>
                                    <li class="row-button">
                                        <c:choose>
                                            <c:when test="${employee.type == TYPE_ENUM.KEEPER}">
                                                <a
                                                    href="/${employee.id}/asignarespecies"
                                                    class="simple-button UDbutton">
                                                    Asignar/Remover Especies
                                                </a>
                                            </c:when>
                                            <c:when test="${employee.type == TYPE_ENUM.GUIDE}">
                                                <a
                                                    href="/${employee.id}/asignaritinerarios"
                                                    class="simple-button UDbutton">
                                                    Asignar/Remover Itinerarios
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                            </c:otherwise>
                                        </c:choose>
                                    </li>
                                </ul>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </main>
        <div class="confirm_delete--container hidden" id="confirm_delete">
            <p class="confirm_delete--txt">¿Esta seguro que desea eliminar este empleado?</p>
            <div class="button-container">
                <form method="post" action="/error" id="delete_form">
                    <input class="hidden" type="text" name="id" id="id" required>
                    <input type="submit" class="simple-button simple-button--delete" value="Eliminar">
                </form>
                <button class="simple-button gray-button" id="cancel_delete">
                    No Eliminar
                </button>
            </div>
        </div>
        <script>
            //CONFIRM DELETE CONSTS
            const deleteBtns = Array.from(document.getElementsByName('delete_employee'));
            const container = document.getElementById('confirm_delete');
            const btn_cancel = document.getElementById('cancel_delete');
            const delete_form = document.getElementById('delete_form');
            const delete_id = document.getElementById('id');

            //EMPLOYEE PANEL CONSTS
            const panelBtns = Array.from(document.getElementsByName('show_employee_panel'));
            const closePanelBtns = Array.from(document.getElementsByName('close_panel'));

            //CONFIRM DELETE PANEL
            deleteBtns.forEach(function (btn) {
                btn.addEventListener('click', function () {
                    const id = extractEmployeeId(btn.id);
                    delete_form.action = "/eliminar_empleado";
                    delete_id.value = id;
                    container.classList.remove('hidden');
                });
            });
            btn_cancel.addEventListener('click', function () {
                container.classList.add('hidden');
            });

            //EMPLOYEE PANEL
            panelBtns.forEach(function (btn) {
                btn.addEventListener('click', function () {
                    const id = extractEmployeeId(btn.id);
                    const panel = document.getElementById('employeePanel-' + id);
                    const panels = Array.from(document.getElementsByName('employee_panel'));
                    panels.forEach(function (p) {
                        if (!p.classList.contains('hidden'))
                            p.classList.add('hidden')
                    });
                    panel.classList.remove('hidden');
                });
            });
            closePanelBtns.forEach(function (btn) {
                btn.addEventListener('click', function () {
                    const id = extractEmployeeId(btn.id);
                    const panel = document.getElementById('employeePanel-' + id);
                    panel.classList.add('hidden');
                });
            });

            function extractEmployeeId(id) {
                return id.substring(id.indexOf("-") + 1, id.length);
            }
        </script>
    </body>
</html>
