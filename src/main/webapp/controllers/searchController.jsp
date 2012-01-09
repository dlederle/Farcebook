<%@ page import = "java.util.*"%>
<%@ page import = "farcebook.*"%>

<%

Repository repo = Repository.instance();
User currentUser = (User) session.getAttribute("currentUser");
if(currentUser == null || request.getParameter("submit") == null){
     response.sendRedirect("../index.jsp");
}
String search = request.getParameter("search");
if(search == ""){
     response.sendRedirect(request.getHeader("referer"));
}

ArrayList<User> userMatches = new ArrayList<User>();
ArrayList<Group> groupMatches = new ArrayList<Group>();
ArrayList<Event> eventMatches = new ArrayList<Event>();
String searches[] = search.split(" ");
for (int i = 0; i<searches.length; i++){

     ArrayList<User> tempUserList = repo.getUsersByName(searches[i]);
     for (int j = 0; j<tempUserList.size(); j++){
          userMatches.add(tempUserList.get(j));
     }

     ArrayList<Event> tempEventList = new ArrayList<Event>();
     tempEventList = repo.getEventsByName(searches[i]);
     for (int j = 0; j<tempEventList.size(); j++){
          eventMatches.add(tempEventList.get(j));
     }


     ArrayList<Group> tempGroupList = new ArrayList<Group>();
     tempGroupList = repo.getGroupsByName(searches[i]);
     for (int j = 0; j<tempGroupList.size(); j++){
          groupMatches.add(tempGroupList.get(j));
     }
}
session.setAttribute("groupMatches", groupMatches);
session.setAttribute("eventMatches", eventMatches);
session.setAttribute("userMatches", userMatches);


response.sendRedirect("../home.jsp?search=" + search);
%>

