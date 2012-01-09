<%@ page import = "farcebook.*"%>
<%@ page import = "java.util.*"%>
<%
if (session.getAttribute("currentUser") != null){
	Repository repo = Repository.instance();

	User currentUser = (User) session.getAttribute("currentUser");
	String entityID = request.getParameter("ID");	
	Entity profileEntity = repo.getEntity(entityID);
	if(profileEntity!=null) {
		%>
<!doctype html>
     <html>
	<head>
		<title><%= profileEntity.getDisplayName() %>'s profile | Farcebook </title>
	     <link rel="stylesheet" href="etc/styles/bootstrap.min.css">
		<link rel="stylesheet" href="etc/styles/farcebook.css">

		<link rel="icon" type="image/ico" href="etc/images/favicon.ico"/>

		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js"></script>
		<script src="etc/js/bootstrap-tabs.js"></script>

	</head>
	
     <body>
	<jsp:include page="views/menu2.jsp"/>
	<div class= "container-fluid">
		<div class="sidebar">
			<div class="photo">
			     <img src="<%=profileEntity.getProfilePicture().getSource()%>" width=100 height=auto></img>
			     <%
			     if(!currentUser.getID().equals(entityID) && 
					!profileEntity.getSubscribedTo().contains(currentUser) && 
					!profileEntity.getSubscriberType().equals("Attendee")) {
				     out.print("<form action=controllers/friendController.jsp?" + entityID + " method=get>");
				     out.print("<br />");
				     out.print("<input type=hidden name=action value=" + profileEntity.getSubscriberAction() + "></input>");
				     out.print("<input type=hidden name=ID value=" + entityID + " ></input>");
				     out.print("<input type=submit name=submit value=" + profileEntity.getSubscriberAction() +"></input>");
				     out.print("<br />");
				     out.print("</form>");
			     }%>
		     <h2> <%=profileEntity.getDisplayName()%> </h2>
			<%
			if(profileEntity.isAdmin(currentUser) && 
                    !profileEntity.getSubscriberType().equals("Friend")){
					out.print("<form action=views/invite.jsp method=get>");
				     out.print("<input type=hidden name=ID value=" + entityID + " ></input>");
					out.print("<input type=submit name=submit value='Invite Friends'></input>");
					out.print("</form>");

			}

			if(profileEntity.getStatus() != null){         
				out.print(profileEntity.getStatus());	
			}
		%>   
                    </div>
                    <hr>
                    <div class="info">
	                    <form action = "controllers/dislikeController.jsp" method = "GET">
		                    <input type = hidden name = ID value ="<%=entityID%>"></input>
		                    <input class ="btn large danger" type ="submit" id="DerkaDislike" value="Dislike: <%=profileEntity.getDislikes()%>"></input>
	                    </form> 
	                    <%=profileEntity.getInfo()%>
                    </div>

	          </div> <!---End Sidebar-->
	     <%
		String name = profileEntity.getDisplayName();
		if(profileEntity.getSubscriberType() == "Friend") {
			name = profileEntity.getDisplayName().split("\"")[1];
		}
		%>

		     <div class="content">
			     <ul class="tabs">
			          <li class="active"><a href="#wall"><%=name%>'s Wall</a></li>
			          <li><a href="#members"><%=name%>'s <%=profileEntity.getSubscriberType()%>s</a></li>
			          <li><a href="#groups"><%=name%>'s Groups</a></li>
			          <li><a href="#events"><%=name%>'s Events</a></li>
			          <li><a href="#photos">Photos</a></li>
			     </ul>

			     <div class="pill-content">
                         <div class="active" id="wall">
			               <jsp:include page = "views/wall.jsp"/>
			          </div>

			          <div id="members">
			               <jsp:include page = "views/entityList.jsp?type=user"/>
			          </div>

			          <div id ="groups">
			               <jsp:include page = "views/entityList.jsp?type=group"/>
			          </div>

			          <div id ="events">
			               <jsp:include page = "views/entityList.jsp?type=event"/>
			          </div>

			          <div id="photos">
			               <jsp:include page = "views/albums.jsp"/>
			          </div>
		     	
                    </div> <!---End pillcontent-->
		     </div> <!---End-contend-->
	     </div> <!--End container-->
     </body>

	<script>
	     $(function () {
			$('.tabs').tabs()
		})
	</script>
	<%
	} else{
		response.sendRedirect("index.jsp");
	}
}

%>
</html>
