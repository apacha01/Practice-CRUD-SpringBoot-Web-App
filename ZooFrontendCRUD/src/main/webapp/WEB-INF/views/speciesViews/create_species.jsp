<%-- 
    Document   : create_species
    Created on : 22 feb 2023, 22:27:51
    Author     : Agustín Pacheco
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@page import="p2.zoofrontendcrud.auxiliar.TYPE_ENUM"%>
<c:import url="../sessionCheck.jsp"></c:import>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crear Especie</title>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/form.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    </head>
    <body>
        <h1>Ingrese los datos de la especie</h1>
        <form method="post" action="/crear_especie" class="create_form">
            <input class="form_input" type="text" name="name" placeholder="Nombre" required>
            <input class="form_input" type="text" name="scientificName" placeholder="Nombre Cientifico" required>
            <textarea class="form_textarea" type="text" name="description" placeholder="Descripcion" required></textarea>
            <select class="form_input" name="zoneName">
                <c:forEach var="zone" items="${zones}">
                    <option>${zone.name} (id=${zone.id})</option>
                </c:forEach>
            </select>
            <input class="form_button" type="submit">
        </form>
        <br/>
        <p>${errorMsg}</p>
    </body>
</html>
