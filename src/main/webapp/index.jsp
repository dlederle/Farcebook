<%@ page import = "farcebook.*"%>
<!doctype html>

<html>
<head>
     <title>Farcebook</title>
     <link rel="stylesheet" href="etc/styles/bootstrap.min.css">
     <link rel="stylesheet" href="etc/styles/farcebook.css">
     <link rel="icon" type="image/ico" href="etc/images/favicon.ico"/>
</head>

<body>

<%

     if (session.getAttribute("currentUser") != null) {
          User currentUser = (User) session.getAttribute("currentUser");
          response.sendRedirect("home.jsp");
     }
     else {
%>
     <jsp:include page="views/menu.jsp"/>
     
     <div class="page-header">
          </br>
          <h1>Welcome to Farcebook!</h1>
          <p>"It's not a bug, its a feature!" - Gang of Four, 2011<p>
     </div>
     
     <jsp:include page = "views/register.jsp"/>
<%}%>

</body>
</html>
