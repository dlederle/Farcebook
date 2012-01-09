<%@ page import = "java.util.*"%>
<%@ page import = "farcebook.*" %>

<%
User currentUser = (User)session.getAttribute("currentUser");
if(currentUser==null || request.getParameter("submit") == null) {
     response.sendRedirect("../index.jsp");
}
else {

     Repository repo = Repository.instance();

     String entityID = request.getParameter("ID");
     Entity profileEntity = repo.getEntity(entityID);

     String statusText = request.getParameter("statusText");
     String commentText = request.getParameter("commentText");
     String postText = request.getParameter("postText");
     String pictureCommentText = request.getParameter("pictureCommentText");
     String pictureCommentCommentText = request.getParameter("pictureCommentCommentText");

     if(statusText !=null && !statusText.equals("")){
          Date now = new Date();
          profileEntity.setStatus(statusText);
          profileEntity.post(statusText, currentUser, now);
     }

     if(commentText !=null && !commentText.equals("")){
          int i = Integer.parseInt(request.getParameter("postNum"));
          Date now = new Date();
          profileEntity.comment(commentText, currentUser, now, i);
     }

     if(postText !=null && !postText.equals("")){
          Date now = new Date();
          profileEntity.post(postText, currentUser, now);
     }

     if (pictureCommentText != null && !pictureCommentText.equals(""))
     {
          Date now = new Date();
          Post picturePost = profileEntity.makePost(pictureCommentText, currentUser, now);
          Album album = profileEntity.getAlbum(request.getParameter("album"));
          Picture picture = album.getPicture(request.getParameter("picture")); 
          picture.addPost(picturePost);
     }

     if (pictureCommentCommentText != null && !pictureCommentCommentText.equals(""))
     {
          Date now = new Date();
          int i = Integer.parseInt(request.getParameter("postNum"));
          Album album = profileEntity.getAlbum(request.getParameter("album"));
          Picture picture = album.getPicture(request.getParameter("picture")); 
          picture.getPicturePosts().get(i).comment(pictureCommentCommentText, currentUser, now);
     }

     response.sendRedirect("../profile.jsp?ID=" + entityID);
}
%>
