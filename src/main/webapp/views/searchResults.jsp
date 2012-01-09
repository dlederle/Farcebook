<%@ page import = "java.util.*"%>
<%@ page import = "farcebook.*"%>

<%
     Repository repo = Repository.instance();
     User currentUser = (User) session.getAttribute("currentUser");
     if(currentUser == null) {
          response.sendRedirect("/index.jsp");
     }
     String search = request.getParameter("search");
     ArrayList<User> userMatches = (ArrayList<User>) session.getAttribute("userMatches");
     ArrayList<Group> groupMatches = (ArrayList<Group>) session.getAttribute("groupMatches");
     ArrayList<Event> eventMatches = (ArrayList<Event>) session.getAttribute("eventMatches");
     if(userMatches==null || groupMatches==null || eventMatches==null) {
          response.sendRedirect("home.jsp"); //Whats a better way to handle that error
     }
     if(userMatches.size() == 0 && groupMatches.size() == 0 && eventMatches.size() == 0) {
          out.print("<h1>No Results, please search again</h1>");
     } else {

%>
     <div class="2/3"a>
               <h3>User Matches</h3>
               <%
		     for (int i = 0; i<userMatches.size(); i++){
			%>
                    <a href="profile.jsp?ID=<%=userMatches.get(i).getID()%>"><%=userMatches.get(i).getDisplayName()%></a>
                    </br>
			<%}%>
               <h3>Group Matches</h3>
               <%
        	     for (int i = 0; i<groupMatches.size(); i++){
		     %>
                    <a href="profile.jsp?ID=<%=groupMatches.get(i).getID()%>"><%=groupMatches.get(i).getDisplayName()%></a>
                    </br>
               <%}%>
               <h3>Event Matches</h3>

        	     <%
          	for (int i = 0; i<eventMatches.size(); i++){
               %>
                    <a href="profile.jsp?ID=<%=eventMatches.get(i).getID()%>"><%=eventMatches.get(i).getDisplayName()%></a>
                    </br>
               <%}%>
     </div>

     <%}%>
