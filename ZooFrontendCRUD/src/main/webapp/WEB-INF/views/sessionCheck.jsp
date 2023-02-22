<%-- 
    Document   : sessionCheck
    Created on : 22 feb 2023, 18:53:40
    Author     : Agustín Pacheco
--%>

<%@page import="p2.zoofrontendcrud.auxiliar.TYPE_ENUM"%>
<%
    HttpSession _session = request.getSession();

    if (_session.getAttribute("employeeUserName") == null || _session.getAttribute("employeeType") == null
            || _session.getAttribute("employeeType") != TYPE_ENUM.ADMIN) {
        out.print("<script>location.replace('/login');</script>");
    }
%>
