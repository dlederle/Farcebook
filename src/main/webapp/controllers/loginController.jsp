<%@ page import = "java.util.*"%>
<%@ page import = "farcebook.*"%>

<%
if(request.getParameter("submit") != null) {

      Repository repo = Repository.instance();
      String email = request.getParameter("email");
      String pass = request.getParameter("password");
      if (!email.equals("") && !pass.equals("") && repo.getUserByEmail(email) != null){
              User currentUser = (User) repo.getUserByEmail(email);
              session.setAttribute("currentUser", currentUser);
              response.sendRedirect("../index.jsp");
      }
      else {
          response.sendRedirect("../index.jsp?badMatch=1");
	 }
}
else {
     response.sendRedirect("../index.jsp");
}
%>
