<%-- 
    Document   : assignHabitats
    Created on : 1 mar 2023, 10:40:04
    Author     : Agustín Pacheco
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<c:import url="../sessionCheck.jsp"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Asignar Habitats</title>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/table.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/button.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/form.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    </head>
    <body>
        <h1>Los habitats de la especie: "${name}" están tildadas. 
            Tilde los que desee agregar o destilde los que desee quitarle:
        </h1>
        <form onsubmit="return reviewHabitats();" method="post" class="assign_form">
            <table class="table">
                <thead>
                    <tr>
                        <th class="table__column--s">¿Asignar?</th>
                        <th class="table__column--s">ID</th>
                        <th class="table__column--s">Nombre</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="habitat" items="${speciesHabitats}" >
                        <tr>
                            <td class="td">
                                <input type="hidden" name="toBeRemoved" value="${habitat.id}" id="${habitat.id}"/>
                                <input class="form_checkbox" 
                                       type="checkbox" 
                                       name="alreadyAssigned"
                                       checked
                                       value="${habitat.id}"/>
                            </td>
                            <td class="td">${habitat.id}</td>
                            <td class="td">${habitat.name}</td>
                        </tr>
                    </c:forEach>
                    <c:forEach var="habitat" items="${habitats}" >
                        <tr>
                            <td class="td">
                                <input class="form_checkbox" 
                                       type="checkbox" 
                                       name="toBeAssigned"
                                       value="${habitat.id}"/>
                            </td>
                            <td class="td">${habitat.id}</td>
                            <td class="td">${habitat.name}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <input class="simple-button gray-button" type="submit"/>
        </form>
        <script>
            function reviewHabitats() {
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
