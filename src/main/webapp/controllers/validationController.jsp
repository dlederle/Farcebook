<%@ page import = "java.util.*" %>
<%@ page import = "farcebook.*" %>

<%

String userID = request.getParameter("ID");

Repository repo = Repository.instance();
User currentUser = (User) repo.getEntity(userID);
if(currentUser!=null){
     currentUser.verify();

     session.setAttribute("currentUser", currentUser);
     response.sendRedirect("../home.jsp");
}
else {
     response.sendRedirect("../answerEmail.jsp?noSuch=1");
}

%>
