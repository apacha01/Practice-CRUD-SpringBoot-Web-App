<%-- 
    Document   : login
    Created on : 9 feb 2023, 19:15:33
    Author     : Agustín Pacheco
--%>

<%@page import="p2.zoofrontendcrud.auxiliar.LoginSession"%>
<%@page import="p2.zoofrontendcrud.auxiliar.TYPE_ENUM"%>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/form.css"/>
        <link rel="stylesheet" href="css/style.css"/>
    </head>
    <body>
        <h1>Ingrese su usuario y contraseña</h1>
        <form class="login_form" method="post" action="login.jsp">
            <input class="form_input" type="text" name="userName" placeholder="Nombre de Usuario" required>
            <input class="form_input" type="password" name="password" placeholder="Contraseña" required>
            <input class="form_button" type="submit" name="btnLogin" value="Ingresar">
        </form>
        <h2>${errorMsg}</h2>
        <%
            LoginSession ls = new LoginSession();
            HttpSession _session = request.getSession();
            
            if (request.getParameter("btnLogin") != null) {
                String usName = request.getParameter("userName");
                String pass = request.getParameter("password");
                String loginMsg = ls.login(usName, pass);

                if ("OK".equals(loginMsg)) {
                    TYPE_ENUM type = ls.getEmployeeType(usName);
                    _session.setAttribute("employeeUserName", usName);
                    _session.setAttribute("employeeType", type);
                    switch (type) {
                        case ADMIN:
                            response.sendRedirect("/menu_admin");
                            break;
                        case KEEPER:
                            response.sendRedirect("/menu_cuidador");
                            break;
                        case GUIDE:
                            response.sendRedirect("/menu_guia");
                            break;
                        default:
                            response.sendRedirect("/error");
                            break;
                    }
                }
                else {
                    out.print(loginMsg);
                }
            }
            if (request.getParameter("cerrar") != null) {
                session.invalidate();
            }
        %>
    </body>
</html>
