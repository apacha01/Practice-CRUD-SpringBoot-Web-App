<%-- 
    Document   : habitats
    Created on : 25 feb 2023, 10:02:05
    Author     : Agustín Pacheco
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<c:import url="../sessionCheck.jsp"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Habitats</title>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/table.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/button.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    </head>
    <body>
        <h1>Habitats</h1>
        <h2>${errorMsg}</h2>
        <main class="container">
            <table class="table">
                <thead>
                    <tr>
                        <th class="table__column--es">ID</th>
                        <th class="table__column--s">Nombre</th>
                        <th class="table__column--s">Clima</th>
                        <th class="table__column--s">Vegetacion</th>
                        <th class="table__column--s">Continentes donde se encuentra</th>
                        <th class="table__column--m table__align--center">
                            <a
                                href="./crear_habitat"
                                class="simple-button simple-button--add">
                                Nuevo Habitat
                            </a>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="h" items="${habitats}">
                        <tr>
                            <td class="td">${h.id}</td>
                            <td class="td">${h.name}</td>
                            <td class="td">${h.weather}</td>
                            <td class="td">${h.vegetation}</td>
                            <td class="td cell-button">
                                <c:forEach var="continent" items="${h.continents}" >
                                    <p class="table__align--center">${continent.name}</p>
                                </c:forEach>
                                <a
                                    href="./${h.id}/asignarcontinentes"
                                    class="simple-button UDbutton in-cell_UDbutton">
                                    Asignar/Remover Continentes
                                </a>
                            </td>
                            <td class="td">
                                <ul class="table__button-control">
                                    <div class="UDops-habitat-button-container">
                                        <li>
                                            <a
                                                href="./editar_habitat/${h.id}"
                                                class="simple-button simple-button--edit bigger-button">
                                                Editar
                                            </a>
                                        </li>
                                        <li>
                                            <button class="simple-button simple-button--delete bigger-button"
                                                    id="delete-${h.id}"
                                                    name="delete_habitats">
                                                Eliminar
                                            </button>
                                        </li>
                                    </div>
                                    <li class="row-button">
                                        <a
                                            href="./${h.id}/asignarespecies"
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
            <p class="confirm_delete--txt">¿Esta seguro que desea eliminar este habitat?</p>
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
            const deleteBtns = Array.from(document.getElementsByName('delete_habitats'));
            const container = document.getElementById('confirm_delete');
            const btn_cancel = document.getElementById('cancel_delete');
            const delete_form = document.getElementById('delete_form');
            const delete_id = document.getElementById('id');

            //CONFIRM DELETE PANEL
            deleteBtns.forEach(function (btn) {
                btn.addEventListener('click', function () {
                    const id = extractId(btn.id);
                    delete_form.action = "./eliminar_habitat";
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