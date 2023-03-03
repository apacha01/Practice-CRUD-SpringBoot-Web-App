<%-- 
    Document   : create_habitat
    Created on : 2 mar 2023, 14:28:52
    Author     : agust
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<c:import url="../sessionCheck.jsp"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crear Habitat</title>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/form.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    </head>
    <body>
        <h1>Ingrese los datos del empleado</h1>
        <form method="post" class="create_form">
            <input class="form_input" type="text" name="name" placeholder="Nombre" required>
            <input class="form_input" type="text" name="weather" placeholder="Clima" required>
            <input class="form_input" type="text" name="vegetation" placeholder="Vegetacion" required>
            <input class="form_button" type="submit">
        </form>
        <br/>
        <p>${errorMsg}</p>
    </body>
</html>
