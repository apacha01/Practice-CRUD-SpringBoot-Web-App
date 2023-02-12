<%-- 
    Document   : employees
    Created on : 12 feb 2023, 1:42:54
    Author     : Agustín Pacheco
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    <h1>Empleados</h1>
    <main class="clientes-container">
        <table class="table">
            <thead>
                <tr>
                    <th class="table__column--s">Ocupación</th>
                    <th class="table__column--s">Nombre</th>
                    <th class="table__column--m">Nombre de Usuario</th>
                    <th class="table__column--s">Direccion</th>
                    <th class="table__column--s">Telefono</th>
                    <th class="table__column--s">Dia de alta</th>
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
                        <td class="td">${employee.type}</td>
                        <td class="td">${employee.name}</td>
                        <td class="td">${employee.user_name}</td>
                        <td class="td">${employee.address}</td>
                        <td class="td">${employee.phone}</td>
                        <td class="td">${employee.first_day}</td>
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
                                    <a
                                        href="/eliminar_empleado/${employee.id}"
                                        class="simple-button simple-button--delete">
                                        Eliminar
                                    </a>
                                </li>
                            </ul>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
</body>
</html>
