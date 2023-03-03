<%-- 
    Document   : assignZones
    Created on : 3 mar 2023, 16:24:24
    Author     : agust
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<c:import url="../sessionCheck.jsp"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Asignar Zonas</title>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/table.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/button.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/form.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    </head>
    <body>
        <h1>Las zonas de ${name} están tildados. Tilde los que desee agregar o destilde los que desee quitarle:</h1>
        <form onsubmit="return reviewZones();" method="post" class="assign_form">
            <table class="table">
                <thead>
                    <tr>
                        <th class="table__column--es">¿Asignar?</th>
                        <th class="table__column--es">ID</th>
                        <th class="table__column--s">Nombre</th>
                        <th class="table__column--s">Extension</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="zone" items="${assignedZones}" >
                        <tr>
                            <td class="td">
                                <input type="hidden" name="toBeRemoved" value="${zone.id}" id="${zone.id}"/>
                                <input class="form_checkbox" 
                                       type="checkbox" 
                                       name="alreadyAssigned"
                                       checked
                                       value="${zone.id}"/>
                            </td>
                            <td class="td">${zone.id}</td>
                            <td class="td">${zone.name}</td>
                            <td class="td">${zone.extension}</td>
                        </tr>
                    </c:forEach>
                    <c:forEach var="zone" items="${zones}" >
                        <tr>
                            <td class="td">
                                <input class="form_checkbox" 
                                       type="checkbox" 
                                       name="toBeAssigned"
                                       value="${zone.id}"/>
                            </td>
                            <td class="td">${zone.id}</td>
                            <td class="td">${zone.name}</td>
                            <td class="td">${zone.extension}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <input class="simple-button gray-button" type="submit"/>
        </form>
        <script>
            function reviewZones() {
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