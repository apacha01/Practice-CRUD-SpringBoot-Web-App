<%-- 
    Document   : assignItineraries
    Created on : 18 feb 2023, 15:03:10
    Author     : Agustín Pacheco
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<c:import url="../sessionCheck.jsp"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Asignar Itinerarios</title>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/table.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/button.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/form.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    </head>
    <body>
        <h1>Los itinerarios de ${name} están tildados. Tilde los que desee agregar o destilde los que desee quitarle:</h1>
        <h3 style="color: cornflowerblue;">
            (Aclaración: si un itinerario está asignado no se puede asignar otra vez.)
        </h3>
        <form onsubmit="return reviewItineraries();" method="post" class="assign_form">
            <table class="table">
                <thead>
                    <tr>
                        <th class="table__column--es">¿Asignar?</th>
                        <th class="table__column--es">ID</th>
                        <th class="table__column--s">Codigo</th>
                        <th class="table__column--s">Duracion</th>
                        <th class="table__column--m">Largo del recorrido</th>
                        <th class="table__column--s">Visitantes maximos</th>
                        <th class="table__column--g">Numero de especies visitadas</th>
                        <th class="table__column--es">Asignado</th>
                        <th class="table__column--m">Zonas recorridas</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="itinerary" items="${assignedItineraries}" >
                        <tr>
                            <td class="td">
                                <input type="hidden" 
                                       name="toBeRemoved" 
                                       value="${itinerary.id}" 
                                       id="${itinerary.id}"/>
                                <input class="form_checkbox" 
                                       type="checkbox" 
                                       name="alreadyAssigned"
                                       checked
                                       value="${itinerary.id}"/>
                            </td>
                            <td class="td">${itinerary.id}</td>
                            <td class="td">${itinerary.code}</td>
                            <td class="td">${itinerary.duration}</td>
                            <td class="td">${itinerary.route_length}</td>
                            <td class="td">${itinerary.max_people}</td>
                            <td class="td">${itinerary.num_species_visited}</td>
                            <td class="td"><p>Si</p></td>
                            <td class="td">
                                <p>
                                    <c:forEach var="zone" items="${itinerary.covered_zones}">
                                        ${zone.name} 
                                    </c:forEach>
                                </p>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:forEach var="itinerary" items="${itineraries}">
                        <tr>
                            <td class="td">
                                <c:if test="${not itinerary.assigned}">
                                    <input class="form_checkbox" 
                                           type="checkbox" 
                                           name="toBeAssigned"
                                           value="${itinerary.id}"/>
                                </c:if>
                            </td>
                            <td class="td">${itinerary.id}</td>
                            <td class="td">${itinerary.code}</td>
                            <td class="td">${itinerary.duration}</td>
                            <td class="td">${itinerary.route_length}</td>
                            <td class="td">${itinerary.max_people}</td>
                            <td class="td">${itinerary.num_species_visited}</td>
                            <td class="td">
                                <c:choose>
                                    <c:when test="${itinerary.assigned}">
                                        <p>Si</p>
                                    </c:when>
                                    <c:otherwise>
                                        <p>No</p>  
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="td">
                                <p>
                                    <c:forEach var="zone" items="${itinerary.covered_zones}">
                                        ${zone.name} 
                                    </c:forEach>
                                </p>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <input class="simple-button gray-button" type="submit"/>
        </form>
        <script>
            function reviewItineraries() {
                const alreadyAssigned = Array.from(document.getElementsByName('alreadyAssigned'));
                alreadyAssigned.forEach(function (chbox) {
                    if (chbox.checked)
                        document.getElementById(chbox.value).disabled = true;
                });
                return true;
            }
        </script>
    </body>
</html>
