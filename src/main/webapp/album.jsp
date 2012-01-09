<%@ page import = "java.util.*" %>
<%@ page import = "farcebook.*" %>
<%@ page import = "com.oreilly.servlet.MultipartRequest" %>
<%
User currentUser = (User) session.getAttribute("currentUser");

if (currentUser != null)
{
     Repository repo = Repository.instance();
     Entity profileEntity = repo.getEntity(request.getParameter("ID"));
     Album album = profileEntity.getAlbum(request.getParameter("album"));
     %>

          <!doctype html>
          <html>
          <head>
          <title><%=album.getTitle()%> | Farcebook</title>
          <link rel="stylesheet" href="etc/styles/bootstrap.min.css">
          <link rel="stylesheet" href="etc/styles/farcebook.css">
          <link rel="icon" type="image/ico" href="etc/images/favicon.ico"/>
          </head>
          <body>
          <jsp:include page="/views/menu2.jsp"/>
          <div class="container">
          <div class="content">

          <div class="row">
          <div class="span8">
          <%
          for (int i = 0; i < album.getPictures().size(); i++)
          {
               if (repo.getEntity(currentUser.getID()).equals(profileEntity))
               {
                    out.print("<a href=controllers/pictureController.jsp?album=" + album.getID()
                              + "&picture=" + album.getPictures().get(i).getID() 
                              + "&profilePicture=yes" + "&ID=" + profileEntity.getID() + "> Make profile picture </a>");
                    {
                         out.print("<a href=\"views/picture.jsp?profilePicture=no&picture=" + album.getPictures().get(i).getID() +
                              "&album=" + album.getID() + "&ID=" + profileEntity.getID() + "\">"
                               + "<img src=\"" + album.getPictures().get(i).getSource() + "\" width=100 height=auto /> </a> <br />");
                    }
               }
          }
     %>
          </div>
          <div class="span8">
          <%
          if (repo.getEntity(currentUser.getID()).equals(profileEntity))
          {
               out.print("Add a picture to this album: <br />");
               out.print("<form action=controllers/pictureController.jsp?profilePicture=no&album=" + album.getID() + " method=post enctype=\"multipart/form-data\" />");
               out.print("<input type=file name=filename /> <br />");
               out.print("<input type=submit value=Submit /> <br />");
               out.print("</form>");
          }

}
%>
</div>
</div>
</div>
</body>
</html>
