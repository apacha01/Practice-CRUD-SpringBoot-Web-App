<%-- 
    Document   : create_itinerary
    Created on : 3 mar 2023, 10:58:59
    Author     : agust
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<c:import url="../sessionCheck.jsp"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crear Itinerario</title>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/form.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    </head>
    <body>
        <h1>Ingrese los datos del itinerario</h1>
        <form method="post" class="create_form">
            <input class="form_input_large" type="text" name="code" placeholder="Codigo" required>
            <input class="form_input_large" type="time" step="1" name="duration" placeholder="Duracion" required>
            <input class="form_input_large" type="text" name="routeLength" placeholder="Largo de recorrido en m (utilize el '.' para expresar decimales. Ej: 125.37)." required>
            <input class="form_input_large" type="number" name="maxPeople" placeholder="Maximo de personas" required>
            <input class="form_input_large" type="number" name="numSpeciesVisited" placeholder="Numero de especies visitadas" required>
            <select class="form_input_large" name="assigned" id="assignedSelector" onchange="hideGuides()">
                <option value="true">Si</option>
                <option value="false">No</option>
            </select>
            <select class="form_input_large" name="guide" id="guideSelector">
                <c:forEach var="guide" items="${guides}">
                    <option value="${guide.id}">${guide.name}</option>
                </c:forEach>
            </select>
            <input class="form_button_large" type="submit">
        </form>
        <br/>
        <p>${errorMsg}</p>
        <script>
            hideGuides();

            function hideGuides(){
                const assignedSelect = document.getElementById('assignedSelector');
                const guideSelect = document.getElementById('guideSelector');
                let assigned = assignedSelect.value.toLowerCase() === 'true';
                
                if (assigned) {
                    guideSelect.classList.remove('hidden');
                    guideSelector.disabled = false;
                }
                else {
                    guideSelect.classList.add('hidden');
                    guideSelector.disabled = true;
                }
            }
        </script>
    </body>
</html>