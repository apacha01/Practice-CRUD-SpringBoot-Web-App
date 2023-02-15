<%-- 
    Document   : login
    Created on : 9 feb 2023, 19:15:33
    Author     : Agustín Pacheco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <h1>Ingrese su usuario y contraseña</h1>
        <form method="post">
            <input type="text" name="userName" placeholder="Nombre de Usuario" required>
            <input type="password" name="password" placeholder="Contraseña" required>
            <button>Ingresar</button>
            <h2>${errorMsg}</h2>
        </form>
    </body>
</html>
