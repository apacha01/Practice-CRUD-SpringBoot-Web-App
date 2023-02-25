<%-- 
    Document   : zones
    Created on : 24 feb 2023, 8:45:12
    Author     : Agustín Pacheco
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<c:import url="../sessionCheck.jsp"></c:import>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Zonas</title>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/table.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/button.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    </head>
    <body>
        <h1>Zonas</h1>
        <h2>${errorMsg}</h2>
        <main class="container">
            <table class="table">
                <thead>
                    <tr>
                        <th class="table__column--es">ID</th>
                        <th class="table__column--s">Nombre</th>
                        <th class="table__column--s">Extension</th>
                        <th class="table__column--s">Especies</th>
                        <th class="table__column--m table__align--center">
                            <a
                                href="/crear_zona"
                                class="simple-button simple-button--add">
                                Nueva Zona
                            </a>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="z" items="${zones}">
                        <tr>
                            <td class="td">${z.id}</td>
                            <td class="td">${z.name}</td>
                            <td class="td">${z.extension}</td>
                            <td class="td">
                                <c:forEach var="species" items="${z.species}" >
                                    <p>${species.name}</p>
                                </c:forEach>
                            </td>
                            <td class="td">
                                <ul class="table__button-control">
                                    <div class="UDops-bigger-button-container">
                                        <li>
                                            <a
                                                href="/editar_zona/${z.id}"
                                                class="simple-button simple-button--edit bigger-button">
                                                Editar
                                            </a>
                                        </li>
                                        <li>
                                            <button class="simple-button simple-button--delete bigger-button"
                                                    id="delete-${z.id}"
                                                    name="delete_zones">
                                                Eliminar
                                            </button>
                                        </li>
                                    </div>
                                    <li class="row-button">
                                        <a
                                            href="/${z.id}/asignarespecies"
                                            class="simple-button UDbutton-big">
                                            Asignar/Remover Especies
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
            const deleteBtns = Array.from(document.getElementsByName('delete_zones'));
            const container = document.getElementById('confirm_delete');
            const btn_cancel = document.getElementById('cancel_delete');
            const delete_form = document.getElementById('delete_form');
            const delete_id = document.getElementById('id');

            //CONFIRM DELETE PANEL
            deleteBtns.forEach(function (btn) {
                btn.addEventListener('click', function () {
                    const id = extractId(btn.id);
                    delete_form.action = "/eliminar_zona";
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
