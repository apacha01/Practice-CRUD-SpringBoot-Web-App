<%-- 
    Document   : assignSpecies
    Created on : 18 feb 2023, 14:49:22
    Author     : Agustín Pacheco
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@page import="p2.zoofrontendcrud.auxiliar.TYPE_ENUM"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Asignar Especies</title>
        <link rel="stylesheet" href="css/table.css"/>
        <link rel="stylesheet" href="css/style.css"/>
    </head>
    <body>
        <%
            HttpSession _session = request.getSession();

            if (_session.getAttribute("employeeUserName") == null || _session.getAttribute("employeeType") == null
                    || _session.getAttribute("employeeType") != TYPE_ENUM.ADMIN) {
                out.print("<script>location.replace('/login');</script>");
            }
        %>
        <h1>Las especies de ${name} están tildadas. 
            Tilde las que desee agregar o destilde las que desee quitarle:</h1>
        <form onsubmit="return reviewSpecies();" method="post">
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
                            <td class="td">${species.scientificName}</td>
                            <td class="td">${species.description}</td>
                            <td class="td">${species.zone.name}</td>
                            <td class="td">
                                <c:forEach var="habitat" items="${species.habitats}">
                                    <p>${habitat.name} </p>
                                </c:forEach>
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
                            <td class="td">${species.scientificName}</td>
                            <td class="td">${species.description}</td>
                            <td class="td">${species.zone.name}</td>
                            <td class="td">
                                <c:forEach var="habitat" items="${species.habitats}" >
                                    <p>${habitat.name}</p>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <input type="submit"/>
        </form>
        <script>
            function reviewSpecies() {
                const alreadyAssigned = Array.from(document.getElementsByName('alreadyAssigned'));
                alreadyAssigned.forEach(function (chbox){
                    if(chbox.checked) document.getElementById(chbox.value).disabled = true;
                });
                return true;
            }
        </script>
    </body>
</html>
