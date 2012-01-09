<%@ page import = "java.util.*"%>
<%@ page import = "farcebook.*"%>

<%
if (session.getAttribute("currentUser") == null) {
     response.sendRedirect("index.jsp");
}

Repository repo = Repository.instance();
String tmpID = request.getParameter("ID");
Entity currEntity;
if(tmpID != null) {
     currEntity = repo.getEntity(tmpID);
}
else {
     currEntity = (User) session.getAttribute("currentUser");
}
String type = request.getParameter("type");
if(type.equals("user")) {
     ArrayList<User> members = currEntity.getSubscribedTo();
     for(int i=0; i<members.size(); i++) {
          %>
                    <img src="<%=members.get(i).getProfilePicture().getSource()%>" width=100 height=auto ></img>
                    <a href="profile.jsp?ID=<%=members.get(i).getID()%>">
                         <%=members.get(i).getDisplayName()%> </a>
                    </br>
          <%
     }
}

if(type.equals("group")){
     ArrayList<Group> groups = currEntity.getGroups();
     for(int j=0; j<groups.size(); j++) {
          %>
                    <img src="<%=groups.get(j).getProfilePicture().getSource()%>" width=100 height=auto ></img>
                    <a href="profile.jsp?ID=<%=groups.get(j).getID()%>">
                         <%=groups.get(j).getDisplayName()%> </a>
                    </br>
          <%
     }
/*
     out.print("<form action=controllers/inviteController.jsp method=get>");
     out.print("<input type=hidden name=ID value=" +   + " />");
     out.print("<textarea name=postText></textarea>");
     out.print("<input type=submit name=submit value=Post />");
     out.print("</form>");
*/
}

if(type.equals("event")){
     ArrayList<Event> events = currEntity.getEvents();
     for(int k=0; k<events.size(); k++) {
          %>
                    <img src="<%=events.get(k).getProfilePicture().getSource()%>" width=100 height=auto></img>
                    <a href="profile.jsp?ID=<%=events.get(k).getID()%>">
                         <%=events.get(k).getDisplayName()%> </a>
                    </br>
     <%
     }
}
%>
