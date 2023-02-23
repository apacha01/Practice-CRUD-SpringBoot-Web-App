<%-- 
    Document   : assignKeepers
    Created on : 22 feb 2023, 18:49:04
    Author     : Agustín Pacheco
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<c:import url="../sessionCheck.jsp"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Asignar Cuidadores</title>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/table.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/button.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/form.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    </head>
    <body>
        <h1>Los cuidadores de la especie: "${name}" están tildadas. 
            Tilde las que desee agregar o destilde las que desee quitarle:
        </h1>
            <form onsubmit="return reviewKeepers();" method="post" class="assign_form">
            <table class="table">
                <thead>
                    <tr>
                        <th class="table__column--s">¿Asignar?</th>
                        <th class="table__column--s">ID</th>
                        <th class="table__column--s">Nombre</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="keeper" items="${speciesKeepers}" >
                        <tr>
                            <td class="td">
                                <input type="hidden" name="toBeRemoved" value="${keeper.id}" id="${keeper.id}"/>
                                <input class="form_checkbox" 
                                       type="checkbox" 
                                       name="alreadyAssigned"
                                       checked
                                       value="${keeper.id}"/>
                            </td>
                            <td class="td">${keeper.id}</td>
                            <td class="td">${keeper.name}</td>
                        </tr>
                    </c:forEach>
                    <c:forEach var="keeper" items="${keepers}" >
                        <tr>
                            <td class="td">
                                <input class="form_checkbox" 
                                       type="checkbox" 
                                       name="toBeAssigned"
                                       value="${keeper.id}"/>
                            </td>
                            <td class="td">${keeper.id}</td>
                            <td class="td">${keeper.name}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <input class="simple-button gray-button" type="submit"/>
        </form>
        <script>
            function reviewKeepers() {
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
