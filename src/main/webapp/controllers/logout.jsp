<%@ page import="farcebook.*" %>

<%
     session.removeAttribute("currentUser");

     response.sendRedirect("../index.jsp");

%>
