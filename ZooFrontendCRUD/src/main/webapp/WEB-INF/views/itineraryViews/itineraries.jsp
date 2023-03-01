<%-- 
    Document   : itineraries
    Created on : 25 feb 2023, 10:45:02
    Author     : Agustín Pacheco
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<c:import url="../sessionCheck.jsp"></c:import>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Itinerarios</title>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/table.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/button.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    </head>
    <body>
        <h1>Itinerarios</h1>
        <h2>${errorMsg}</h2>
        <main class="container">
            <table class="table">
                <thead>
                    <tr>
                        <th class="table__column--es">ID</th>
                        <th class="table__column--es">Codigo</th>
                        <th class="table__column--es">Duracion</th>
                        <th class="table__column--s">Largo del recorrido</th>
                        <th class="table__column--es">Visitantes maximos</th>
                        <th class="table__column--m">Numero de especies visitadas</th>
                        <th class="table__column--s">Asignado</th>
                        <th class="table__column--g">Zonas recorridas</th>
                        <th class="table__column--g table__align--center">
                            <a
                                href="/crear_itinerario"
                                class="simple-button simple-button--add">
                                Nuevo Itinerario
                            </a>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="i" items="${itineraries}">
                        <tr>
                            <td class="td">${i.id}</td>
                            <td class="td">${i.code}</td>
                            <td class="td">${i.duration}</td>
                            <td class="td">${i.route_length}</td>
                            <td class="td">${i.max_people}</td>
                            <td class="td">${i.num_species_visited}</td>
                            <td class="td">
                                <c:choose>
                                    <c:when test="${i.assigned}">
                                        <p style="text-align: center;">Si</p>
                                        <p style="text-align: center;">(${itinerariesGuides[i.id].name})</p>
                                    </c:when>
                                    <c:otherwise>
                                        <p>No</p>  
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="td">
                                <c:forEach var="zone" items="${i.covered_zones}">
                                    <p>${zone.name}</p>
                                </c:forEach>
                            </td>
                            <td class="td">
                                <ul class="table__button-control">
                                    <div class="UDops-button-container">
                                        <li>
                                            <a
                                                href="/editar_itnierario/${i.id}"
                                                class="simple-button simple-button--edit">
                                                Editar
                                            </a>
                                        </li>
                                        <li>
                                            <button class="simple-button simple-button--delete"
                                                    id="delete-${i.id}"
                                                    name="delete_itineraries">
                                                Eliminar
                                            </button>
                                        </li>
                                    </div>
                                    <li class="row-button">
                                        <a
                                            href="/${i.id}/asignarzonas"
                                            class="simple-button UDbutton-medium">
                                            Asignar/Remover Zonas
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
            <p class="confirm_delete--txt">¿Esta seguro que desea eliminar este itinerario?</p>
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
            const deleteBtns = Array.from(document.getElementsByName('delete_itineraries'));
            const container = document.getElementById('confirm_delete');
            const btn_cancel = document.getElementById('cancel_delete');
            const delete_form = document.getElementById('delete_form');
            const delete_id = document.getElementById('id');

            //CONFIRM DELETE PANEL
            deleteBtns.forEach(function (btn) {
                btn.addEventListener('click', function () {
                    const id = extractId(btn.id);
                    delete_form.action = "/eliminar_itinerario";
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