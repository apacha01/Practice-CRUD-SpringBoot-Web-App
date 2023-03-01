<%-- 
    Document   : update_zone
    Created on : 1 mar 2023, 14:32:43
    Author     : Agustín Pacheco
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<c:import url="../sessionCheck.jsp"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Zona</title>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/form.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    </head>
    <body>
        <h1>Ingrese los datos del empleado</h1>
        <form method="post" action="/editar_zona/${z.id}" class="create_form">
            <input class="form_input_large" type="text" name="name" placeholder="Nombre" value="${z.name}" required>
            <input class="form_input_large" type="text" name="extension" 
                   placeholder="Extension en m2 (utilize el '.' para expresar decimales. Ej: 125.37)"
                   value="${z.extension}"
                   required>
            <input class="form_button_large" type="submit">
        </form>
        <br/>
        <p>${errorMsg}</p>
    </body>
</html>
