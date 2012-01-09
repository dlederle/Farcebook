<%@ page import = "java.util.*" %>
<%@ page import = "farcebook.*" %>
<%@ page import = "com.oreilly.servlet.MultipartRequest" %>
<!doctype html>

<html>
<head>
     <title>Farcebook</title>
     <link rel="stylesheet" href="http://twitter.github.com/bootstrap/1.4.0/bootstrap.min.css">
     <link rel="stylesheet" href="etc/styles/farcebook.css">
     <link rel="icon" type="image/ico" href="etc/images/favicon.ico"/>
</head>

<body>
<div class="container">
<div class="content">
<% 

User currentUser = (User) session.getAttribute("currentUser");

if (currentUser != null)
{
     Repository repo = Repository.instance();
     Entity profileEntity = repo.getEntity(request.getParameter("ID"));
     Album album = profileEntity.getAlbum(request.getParameter("album"));
     Picture picture = album.getPicture(request.getParameter("picture"));

     out.print("<img src=\"../" + picture.getSource() + "\" width=400 height=auto/> <br />");

     out.print("<form action=../controllers/wallController.jsp method=get>");
     out.print("<textarea name=pictureCommentText> </textarea>");
     out.print("<input type=hidden name=ID value=" + profileEntity.getID() + " />");
     out.print("<input type=hidden name=album value=" + album.getID() + " />");
     out.print("<input type=hidden name=picture value=" + picture.getID() + " />");
     out.print("<input type=submit name=submit value=Comment />");
     out.print("</form>");

     for (int i = picture.getPicturePosts().size()-1; i>=0; i--)
     {
          out.print(picture.getPicturePosts().get(i).getDate().toString());
          out.print("<br />");
          out.print(picture.getPicturePosts().get(i).getAuthor().getDisplayName());
          out.print("<br />");
          out.print(picture.getPicturePosts().get(i).getText());
          out.print("<br />");

          out.print("<form action=../controllers/wallController.jsp method=get>");
          out.print("<textarea name=pictureCommentCommentText></textarea>");
          out.print("<input type=hidden name=ID value=" + profileEntity.getID() + " />");
          out.print("<input type=hidden name=postNum value=" + i + " />");
          out.print("<input type=submit name=submit value=Comment />");
          out.print("<input type=hidden name=album value=" + album.getID() + " />");
          out.print("<input type=hidden name=picture value=" + picture.getID() + " />");
          out.print("</form>");

          for (int j = picture.getPicturePosts().get(i).getComments().size()-1; j>=0; j--)
          {
               out.print(picture.getPicturePosts().get(i).getComments().get(j).getDate().toString());
               out.print("<br />");
               out.print(picture.getPicturePosts().get(i).getComments().get(j).getAuthor().getDisplayName());
               out.print("<br />");
               out.print(picture.getPicturePosts().get(i).getComments().get(j).getText());
               out.print("<br />");
          }
     }
}
%>
</div>
</div>
</body>
</html>
