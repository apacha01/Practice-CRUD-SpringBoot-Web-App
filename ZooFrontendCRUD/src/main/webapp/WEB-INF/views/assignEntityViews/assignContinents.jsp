<%-- 
    Document   : assignContinents
    Created on : 2 mar 2023, 15:10:19
    Author     : agust
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<c:import url="../sessionCheck.jsp"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Asignar Continentes</title>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/table.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/button.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/form.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    </head>
    <body>
        <h1>Los continentes del habitat "${name}" están tildadas. 
            Tilde los que desee agregar o destilde los que desee quitarle:
        </h1>
        <form onsubmit="return reviewContinents();" method="post" class="assign_form">
            <table class="table">
                <thead>
                    <tr>
                        <th class="table__column--s">¿Asignar?</th>
                        <th class="table__column--s">ID</th>
                        <th class="table__column--s">Nombre</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="continent" items="${assignedContinents}" >
                        <tr>
                            <td class="td">
                                <input type="hidden" name="toBeRemoved" value="${continent.id}" id="${continent.id}"/>
                                <input class="form_checkbox" 
                                       type="checkbox" 
                                       name="alreadyAssigned"
                                       checked
                                       value="${continent.id}"/>
                            </td>
                            <td class="td">${continent.id}</td>
                            <td class="td">${continent.name}</td>
                        </tr>
                    </c:forEach>
                    <c:forEach var="continent" items="${continents}" >
                        <tr>
                            <td class="td">
                                <input class="form_checkbox" 
                                       type="checkbox" 
                                       name="toBeAssigned"
                                       value="${continent.id}"/>
                            </td>
                            <td class="td">${continent.id}</td>
                            <td class="td">${continent.name}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <input class="simple-button gray-button" type="submit"/>
        </form>
        <script>
            function reviewContinents() {
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
