<%-- 
    Document   : employeeData
    Created on : 4 mar 2023, 9:53:07
    Author     : agust
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="p2.zoofrontendcrud.auxiliar.TYPE_ENUM"%>
<!DOCTYPE html>
<html>
    <head>
        <%
            HttpSession _session = request.getSession();

            if (_session.getAttribute("employeeUserName") == null || _session.getAttribute("employeeType") == null
                    || (_session.getAttribute("employeeType") != TYPE_ENUM.GUIDE
                    && _session.getAttribute("employeeType") != TYPE_ENUM.KEEPER) ) {
                out.print("<script>location.replace('/login');</script>");
            }
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Consultar datos</title>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/table.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    </head>
    <body>
        <h1>Datos del empleado ${employee.name}:</h1>
        <main class="container">
            <table class="table">
                <thead>
                    <tr>
                        <th class="table__column--es">ID</th>
                        <th class="table__column--es">Ocupación</th>
                        <th class="table__column--es">Nombre</th>
                        <th class="table__column--es">Usuario</th>
                        <th class="table__column--es">Direccion</th>
                        <th class="table__column--s">Telefono</th>
                        <th class="table__column--es">Dia de alta</th>
                        <th class="table__column--g">A cargo de</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="td">${employee.id}</td>
                        <td class="td">${employee.type.name}</td>
                        <td class="td">${employee.name}</td>
                        <td class="td">${employee.user_name}</td>
                        <td class="td">${employee.address}</td>
                        <td class="td">${employee.phone}</td>
                        <td class="td">${formatedFirstDay}</td>
                        <td class="td">
                            <c:choose>
                                <c:when test="${employee.type == TYPE_ENUM.KEEPER}">
                                    <c:forEach var="speciesKeeper" items="${empResp}">
                                        <p class="table__align--center">${speciesKeeper.species.name} (id=${speciesKeeper.species.id})</p>
                                        <p class="table__align--center">(Asignada el ${speciesKeeper.assigned_date})</p>
                                    </c:forEach>
                                </c:when>
                                <c:when test="${employee.type == TYPE_ENUM.GUIDE}">
                                    <c:forEach var="guideItinerary" items="${empResp}">
                                        <p class="table__align--center">${guideItinerary.itinerary.code} (id=${guideItinerary.itinerary.id})</p>
                                        <p class="table__align--center">(Asignado el ${guideItinerary.assigned_date})</p>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </tbody>
            </table>
        </main>
    </body>
</html>