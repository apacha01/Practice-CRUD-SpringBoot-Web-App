<%-- 
    Document   : assignSpecies
    Created on : 18 feb 2023, 14:49:22
    Author     : Agustín Pacheco
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<c:import url="../sessionCheck.jsp"></c:import>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Asignar Especies</title>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/table.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/button.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/form.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    </head>
    <body>
        <h1>Las especies de ${name} están tildadas. 
            Tilde las que desee agregar o destilde las que desee quitarle:</h1>
        <form onsubmit="return reviewSpecies();" method="post" class="assign_form">
            <table class="table">
                <thead>
                    <tr>
                        <th class="table__column--s">¿Asignar?</th>
                        <th class="table__column--s">ID</th>
                        <th class="table__column--s">Nombre</th>
                        <th class="table__column--s">Nombre Cientifico</th>
                        <th class="table__column--m">Descripcion</th>
                        <th class="table__column--s">Zona</th>
                        <th class="table__column--m">Habitats</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="species" items="${employeeSpecies}" >
                        <tr>
                            <td class="td">
                                <input type="hidden" name="toBeRemoved" value="${species.id}" id="${species.id}"/>
                                <input class="form_checkbox" 
                                       type="checkbox" 
                                       name="alreadyAssigned"
                                       checked
                                       value="${species.id}"/>
                            </td>
                            <td class="td">${species.id}</td>
                            <td class="td">${species.name}</td>
                            <td class="td">${species.scientific_name}</td>
                            <td class="td">${species.description}</td>
                            <td class="td">${species.zone.name}</td>
                            <td class="td">
                                <p>
                                    <c:forEach var="habitat" items="${species.habitats}" >
                                        ${habitat.name}
                                    </c:forEach>
                                </p>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:forEach var="species" items="${species}" >
                        <tr>
                            <td class="td">
                                <input class="form_checkbox" 
                                       type="checkbox" 
                                       name="toBeAssigned"
                                       value="${species.id}"/>
                            </td>
                            <td class="td">${species.id}</td>
                            <td class="td">${species.name}</td>
                            <td class="td">${species.scientific_name}</td>
                            <td class="td">${species.description}</td>
                            <td class="td">${species.zone.name}</td>
                            <td class="td">
                                <p>
                                    <c:forEach var="habitat" items="${species.habitats}" >
                                        ${habitat.name}
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
            function reviewSpecies() {
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
