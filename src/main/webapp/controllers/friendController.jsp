<%@ page import = "farcebook.*"%>
<%@ page import = "java.util.*"%>
<%
if (session.getAttribute("currentUser") != null && request.getParameter("submit") != null){
	Repository repo = Repository.instance();

	User currentUser = (User) session.getAttribute("currentUser");
	String entityID = request.getParameter("ID");	
	Entity profileEntity = repo.getEntity(entityID);

	String requestNum = request.getParameter("newFriend");
	String action = request.getParameter("action");

	if(entityID != null){
		repo.getEntity(entityID).requestSubscription(currentUser);
	}

	if(requestNum != null){
		int i = Integer.parseInt(requestNum);
		currentUser.getRequests().get(i).accept();
		currentUser.getRequests().remove(i);
	}	

	response.sendRedirect("../home.jsp");
}
else{
	response.sendRedirect("../index.jsp");
}
%>
