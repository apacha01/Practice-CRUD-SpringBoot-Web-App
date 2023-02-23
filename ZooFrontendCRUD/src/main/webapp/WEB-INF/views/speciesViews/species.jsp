<%-- 
    Document   : species
    Created on : 21 feb 2023, 18:57:08
    Author     : Agustín Pacheco
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<c:import url="../sessionCheck.jsp"></c:import>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Especies</title>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/table.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/button.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    </head>
    <body>
        <h1>Especies</h1>
        <h2>${errorMsg}</h2>
        <main class="employees-container">
            <table class="table">
                <thead>
                    <tr>
                        <th class="table__column--es">ID</th>
                        <th class="table__column--s">Nombre</th>
                        <th class="table__column--s">Nombre Cientifico</th>
                        <th class="table__column--s">Descripcion</th>
                        <th class="table__column--s">Zona</th>
                        <th class="table__column--s">Habitats</th>
                        <th class="table__column--s">Cuidadores</th>
                        <th class="table__column--m table__align--right">
                            <a
                                href="/crear_especie"
                                class="simple-button simple-button--add">
                                Nueva Especie
                            </a>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="sp" items="${species}">
                        <tr>
                            <td class="td">${sp.id}</td>
                            <td class="td">${sp.name}</td>
                            <td class="td">${sp.scientific_name}</td>
                            <td class="td">${sp.description}</td>
                            <td class="td">${sp.zone.name}</td>
                            <td class="td">
                                <p>
                                    <c:forEach var="habitat" items="${sp.habitats}" >
                                        ${habitat.name} 
                                    </c:forEach>
                                </p>
                            </td>
                            <td class="td">
                                <p>
                                    <c:forEach var="keeper" items="${speciesKeepers[sp.id]}" >
                                        ${keeper.name} 
                                    </c:forEach>
                                </p>
                            </td>
                            <td class="td">
                                <ul class="table__button-control">
                                    <div class="UDops-button-container">
                                        <li>
                                            <a
                                                href="/editar_especie/${sp.id}"
                                                class="simple-button simple-button--edit">
                                                Editar
                                            </a>
                                        </li>
                                        <li>
                                            <button class="simple-button simple-button--delete"
                                                    id="delete-${sp.id}"
                                                    name="delete_species">
                                                Eliminar
                                            </button>
                                        </li>
                                    </div>
                                    <li class="row-button">
                                        <a
                                            href="/${sp.id}/asignarcuidadores"
                                            class="simple-button UDbutton">
                                            Asignar/Remover Cuidador
                                        </a>
                                    </li>
                                </ul>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </main>
        <div class="confirm_delete--container hidden" id="confirm_delete">
            <p class="confirm_delete--txt">¿Esta seguro que desea eliminar esta especie?</p>
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
            const deleteBtns = Array.from(document.getElementsByName('delete_species'));
            const container = document.getElementById('confirm_delete');
            const btn_cancel = document.getElementById('cancel_delete');
            const delete_form = document.getElementById('delete_form');
            const delete_id = document.getElementById('id');

            //CONFIRM DELETE PANEL
            deleteBtns.forEach(function (btn) {
                btn.addEventListener('click', function () {
                    const id = extractId(btn.id);
                    delete_form.action = "/eliminar_especie";
                    delete_id.value = id;
                    container.classList.remove('hidden');
                });
            });
            btn_cancel.addEventListener('click', function () {
                container.classList.add('hidden');
            });

            function extractId(id) {
                return id.substring(id.indexOf("-") + 1, id.length);
            }
        </script>
    </body>
</html>
