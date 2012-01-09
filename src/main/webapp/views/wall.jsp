<%@ page import = "java.util.*"%>
<%@ page import = "farcebook.*" %>

<%
if (session.getAttribute("currentUser") != null){
     Repository repo = Repository.instance();

     User currentUser = (User) session.getAttribute("currentUser");
     String entityID = request.getParameter("ID");
     Entity profileEntity = repo.getEntity(entityID);

     /*
        if(profileEntity.getID().equals(currentUser.getID())){
        out.print("Update your status: <br/>");
        out.print("<form action=controllers/wallController.jsp method=get>");
        out.print("<input type=text name=statusText />");
        out.print("<input type=hidden name=ID value=" + profileEntity.getID() + " />");
        out.print("<input type=submit name=submit value=Post />");
        out.print("</form>");
        }
      */

     out.print("<hr/>");

     out.print("Post something on " + profileEntity.getDisplayName() + "'s wall:<br/>");
     out.print("<form action=controllers/wallController.jsp method=get>");
     out.print("<input type=hidden name=ID value=" + profileEntity.getID() + " />");
     out.print("<textarea name=postText></textarea>");
     out.print("<input type=submit name=submit value=Post />");
     out.print("</form>");
     out.print("<hr/>");

     if(profileEntity.getWall().getPosts() != null){
          for(int i=profileEntity.getWall().getPosts().size()-1; i>=0; i--){
               out.print("<a href = profile.jsp?ID=" + profileEntity.getWall().getPosts().get(i).getAuthor().getID() + ">" + profileEntity.getWall().getPosts().get(i).getAuthor().getDisplayName() + "</a>");
               out.print("<br/>");
               out.print(profileEntity.getWall().getPosts().get(i).getDate().toString());
               out.print("<br/>");

               String source = profileEntity.getWall().getPosts().get(i).getAuthor().getProfilePicture().getSource(); 
               out.print("<img src=\"" + source + "\" width=50 height=auto /> <br />");

               out.print(profileEntity.getWall().getPosts().get(i).getText());
               out.print("<br/>");
               out.print("<form action=controllers/wallController.jsp method=get>");
               out.print("<textarea name=commentText></textarea>");
               out.print("<input type=hidden name=ID value=" + profileEntity.getID() + " />");
               out.print("<input type=hidden name=postNum value=" + i + " />");
               out.print("<input type=submit name=submit value=Comment />");
               out.print("</form>");
               for(int j=0; j<profileEntity.getWall().getPosts().get(i).getComments().size(); j++){
                    out.print("<a href = profile.jsp?ID=" + profileEntity.getWall().getPosts().get(i).getComments().get(j).getAuthor().getID() + ">" + profileEntity.getWall().getPosts().get(i).getComments().get(j).getAuthor().getDisplayName() + "</a>");
                    out.print("<br />");
                    out.print(profileEntity.getWall().getPosts().get(i).getComments().get(j).getDate().toString());
                    out.print("<br />");
                    source = profileEntity.getWall().getPosts().get(i).getComments().get(j).getAuthor().getProfilePicture().getSource();
                    out.print("<img src=\"" + source + "\" width=50 height=auto /> <br />");     
                    out.print(profileEntity.getWall().getPosts().get(i).getComments().get(j).getText());
                    out.print("<br/>");
               }
               out.print("<hr/>");
          }

     }
}
%>

