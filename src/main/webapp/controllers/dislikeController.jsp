<%@ page import = "java.util.*" %>
<%@ page import = "farcebook.*" %>

<%
if(request.getParameter("ID") != null){
	Repository repo = Repository.instance();
	User currentUser = (User) session.getAttribute("currentUser");
	Entity profileEntity = repo.getEntity(request.getParameter("ID"));
	profileEntity.dislike();
	profileEntity.sendEmail(profileEntity.getEmail(), "Farcebook Update", currentUser.getDisplayName() + " has Disliked your page!\nTAKE YOUR REVENGE here:\nhttp://rosemary.umw.edu:52058/farceRepo/profile.jsp?ID=" + currentUser.getID() +"\n-The Farcebook Team\n Gang of Four");
	response.sendRedirect("../profile.jsp?ID=" + request.getParameter("ID"));
}
%>
