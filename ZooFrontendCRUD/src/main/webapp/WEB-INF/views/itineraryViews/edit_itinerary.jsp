<%-- 
    Document   : edit_itinerary
    Created on : 3 mar 2023, 15:04:10
    Author     : agust
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<c:import url="../sessionCheck.jsp"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Itinerario</title>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/form.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    </head>
    <body>
        <h1>Ingrese los datos del itinerario</h1>
        <form method="post" class="create_form">
            <input class="form_input_large" type="text" name="code" placeholder="Codigo" value="${i.code}" required>
            <input class="form_input_large" type="time" step="1" name="duration" placeholder="Duracion" value="${i.duration}" required>
            <input class="form_input_large" type="text" name="routeLength" 
                   placeholder="Largo de recorrido en m (utilize el '.' para expresar decimales. Ej: 125.37)."
                   value="${i.route_length}"
                   required>
            <input class="form_input_large" type="number" name="maxPeople" placeholder="Maximo de personas" value="${i.max_people}" required>
            <input class="form_input_large" type="number" name="numSpeciesVisited" placeholder="Numero de especies visitadas" value="${i.num_species_visited}" required>
            <select class="form_input_large" name="assigned" id="assignedSelector" onchange="hideGuides()">
                <c:choose>
                    <c:when test="${i.assigned}">
                        <option selected="selected" value="true">Si</option>
                        <option value="false">No</option>
                    </c:when>
                    <c:otherwise>
                        <option value="true">Si</option>
                        <option selected="selected" value="false">No</option>
                    </c:otherwise>
                </c:choose>
            </select>
            <select class="form_input_large" name="guide" id="guideSelector">
                <c:if test="${not empty assignedGuide}">
                    <option selected="selected" value="${assignedGuide.id}">${assignedGuide.name}</option>
                </c:if>
                <c:forEach var="g" items="${guides}">
                    <option value="${g.id}">${g.name}</option>
                </c:forEach>
            </select>
            <input class="form_button_large" type="submit">
        </form>
        <br/>
        <p>${errorMsg}</p>
        <script>
            hideGuides();

            function hideGuides() {
                const assignedSelect = document.getElementById('assignedSelector');
                const guideSelect = document.getElementById('guideSelector');
                let assigned = assignedSelect.value.toLowerCase() === 'true';

                if (assigned) {
                    guideSelect.classList.remove('hidden');
                    guideSelector.disabled = false;
                } else {
                    guideSelect.classList.add('hidden');
                    guideSelector.disabled = true;
                }
            }
        </script>
    </body>
</html>