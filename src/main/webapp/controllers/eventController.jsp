<%@ page import = "farcebook.*"%>
<%@ page import = "java.util.*"%>
<%!
public Date makeDate(String string) {
     String[] tmp = string.split("/");
     ArrayList<Integer> container = new ArrayList<Integer>(3);

     for(int i=0; i<tmp.length; i++) {
          Integer convert = new Integer(tmp[i]);
          container.add(convert);
     }
     Calendar cal = Calendar.getInstance();
     cal.set(container.get(2), container.get(0), container.get(1));
     return cal.getTime();

}
%>

<%
if (session.getAttribute("currentUser") != null && request.getParameter("submit") != null){
	Repository repo = Repository.instance();

	User currentUser = (User) session.getAttribute("currentUser");
     String name = request.getParameter("eventName");
     String description = request.getParameter("description");
     String location = request.getParameter("location");
     String startDate = request.getParameter("start");
     String endDate = request.getParameter("end");
     
     Date start = makeDate(startDate);
     Date end = makeDate(endDate);

     //Add some error checking later


     Event newEvent = repo.createNewEvent(name, start, end, location, description);
     newEvent.subscribeTo(currentUser);
     newEvent.makeAdmin(currentUser);
	currentUser.addEvent(newEvent);
     response.sendRedirect("../profile.jsp?ID=" + newEvent.getID());
}
else {
     response.sendRedirect("../index.jsp");
}
%>
