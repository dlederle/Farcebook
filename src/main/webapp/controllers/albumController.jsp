<%@ page import = "java.util.*"%>
<%@ page import = "farcebook.*"%>

<%

User currentUser = (User) session.getAttribute("currentUser");

Repository repo = Repository.instance();
Entity profileEntity = repo.getEntity(currentUser.getID());

String title = request.getParameter("albumName");

profileEntity.addAlbum(profileEntity.makeAlbum(title));

response.sendRedirect("../home.jsp"); // How do I redirect to tabs?

%>
