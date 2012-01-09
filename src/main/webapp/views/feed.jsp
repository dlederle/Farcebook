<%@ page import = "java.util.*"%>
<%@ page import = "farcebook.*" %>

<%
if (session.getAttribute("currentUser") != null){
     Repository repo = Repository.instance();

     User currentUser = (User) session.getAttribute("currentUser");
     String entityID = request.getParameter("ID");
     %> 
          <h2>Post a status:</h2>
          <form action=controllers/wallController.jsp method=get>
          <input type=text name=statusText />
          <input type=hidden name=ID value="<%= currentUser.getID() %>" />
          <input type=submit name=submit value=Post />
          </form>

          <hr/>
          <%
          ArrayList<Post> myFeed = currentUser.getFeed();
     if(myFeed.get(0) != null){
          for(int i=myFeed.size()-1; i>=0; i--){
               out.print("<div class=span4>");
               out.print("<a href = profile.jsp?ID=" + myFeed.get(i).getAuthor().getID() + ">" + myFeed.get(i).getAuthor().getDisplayName() + "</a>");
               out.print("<br/>");
               out.print(myFeed.get(i).getDate().toString());
               out.print("<br/>");
               out.print("<img src=\"" + myFeed.get(i).getAuthor().getProfilePicture().getSource() + "\" width=50 height=auto></img><br />");
               out.print(myFeed.get(i).getText());
               out.print("<br/>");
               out.print("<form action=controllers/wallController.jsp method=get>");
               out.print("<textarea name=commentText></textarea>");
               out.print("<input type=hidden name=ID value=" + currentUser.getID() + " />");
               out.print("<input type=hidden name=postNum value=" + i + " />");
               out.print("<input type=submit name=submit value=Comment />");
               out.print("</form>");
               for(int j=0; j<myFeed.get(i).getComments().size(); j++){
                    out.print("<a href = profile.jsp?ID=" + myFeed.get(i).getComments().get(j).getAuthor().getID() + ">" + myFeed.get(i).getComments().get(j).getAuthor().getDisplayName() + "</a>");
                    out.print("<br />");
                    out.print(myFeed.get(i).getComments().get(j).getDate().toString());
                    out.print("<br />");
                    out.print("<img src=\"" + myFeed.get(i).getComments().get(j).getAuthor().getProfilePicture().getSource() + "\" width=50 height=auto></img><br />");
                    out.print(myFeed.get(i).getComments().get(j).getText());
                    out.print("<br />");
               }
/*
               String source = myFeed.get(i).getAuthor().getProfilePicture().getSource();
               out.print("<img src=\"" + source +  "\" width=50 height=auto />");
               out.print("<br />");
               out.print(myFeed.get(i).getText());
               out.print("<br />");
               out.print("<form action=controllers/wallController.jsp method=get>");
               out.print("<textarea name=commentText></textarea>");
               out.print("<input type=hidden name=ID value=" + currentUser.getID() + " />");
               out.print("<input type=hidden name=postNum value=" + i + " />");
               out.print("<input type=submit name=submit value=Comment />");
               out.print("</form>");
               for(int j=0; j<myFeed.get(i).getComments().size(); j++){
                    out.print("<a href=profile.jsp?ID=" + myFeed.get(i).getComments().get(j).getAuthor().getID() + ">" + myFeed.get(i).getComments().get(j).getAuthor().getDisplayName() + "</a>");
                    out.print("<br />");
                    
                    out.print(myFeed.get(i).getComments().get(j).getDate().toString());
                    out.print("<br />");

                    source = myFeed.get(i).getComments().get(j).getAuthor().getProfilePicture().getSource();
                    out.print("<img src=\"" + source + "\" width=50 height=auto /><br />");

                    out.print(myFeed.get(i).getComments().get(j).getText());
                    out.print("<br />");
               }
*/
               out.print("<hr/>");
               out.print("</div>");
          }
     }
}
%>
