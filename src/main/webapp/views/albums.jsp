<%@ page import = "java.util.*" %>
<%@ page import = "farcebook.*" %>
<%@ page import = "com.oreilly.servlet.MultipartRequest" %>

<%

User currentUser = (User) session.getAttribute("currentUser");

if (currentUser != null) {
     Repository repo = Repository.instance();
     Entity profileEntity = repo.getEntity(request.getParameter("ID"));

     if (profileEntity == null) {
          profileEntity = repo.getEntity(currentUser.getID());
     }

     %>

     <%
     for (int i = 0; i < profileEntity.getAlbums().size(); i++) {
          %><div class="span-one-third"><%
          try {

               String source = profileEntity.getAlbums().get(i).getPictures().get(0).getSource();
          %>
               <img src="<%=source%>" width=100 height=auto />
          <%
          } catch(Exception e) {;}
          %>
               <a href="album.jsp?album=<%=profileEntity.getAlbums().get(i).getID()%>&ID=<%=profileEntity.getID()%>"> 
               <%=profileEntity.getAlbums().get(i).getTitle() %></a> 
          </div>
          <%
     
     }
     if (repo.getEntity(currentUser.getID()).equals(profileEntity)) {
          out.print("Add a new album: <br />");
          out.print("<form action=controllers/albumController.jsp method=get>");
          out.print("<input type=textbox name=albumName />");
          out.print("<input type=submit name=submit value=submit />");
          out.print("</form>");
     }
}
%>
