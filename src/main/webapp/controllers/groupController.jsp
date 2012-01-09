<%@ page import = "farcebook.*"%>
<%@ page import = "java.util.*"%>
<%
if (session.getAttribute("currentUser") != null && request.getParameter("submit") != null){
	Repository repo = Repository.instance();

	User currentUser = (User) session.getAttribute("currentUser");
     String name = request.getParameter("groupName");
     String description = request.getParameter("description");
     //Add some error checking later

     Group newGroup = repo.createNewGroup(name, description);
     newGroup.subscribeTo(currentUser);
     newGroup.makeAdmin(currentUser);
	currentUser.addGroup(newGroup);
     
     response.sendRedirect("../profile.jsp?ID=" + newGroup.getID());
}
else {
     response.sendRedirect("../index.jsp");
}
%>
