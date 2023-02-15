<%-- 
    Document   : index
    Created on : 9 feb 2023, 19:12:21
    Author     : Agustín Pacheco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio</title>
        <link rel="stylesheet" href="css/style.css"/>
        <link rel="stylesheet" href="css/button.css"/>
    </head>
    <body>
        <h1>Bienvenido al Sistema de Gestion de Información para Ecoparques</h1>
        <div class="index_button-container">
            <button class="big-button simple-button simple-button--add" 
                    onclick="location.href='/login';">
                Ingresar al sistema
            </button>
            <button class="big-button simple-button gray-button">Ver Información</button>
        </div>
    </body>
</html>
