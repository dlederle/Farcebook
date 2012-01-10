<%@ page import = "java.util.*"%>
<%@ page import = "farcebook.*"%>
<%@ page import = "com.oreilly.servlet.MultipartRequest" %> 

<%

User currentUser = (User) session.getAttribute("currentUser");

Repository repo = Repository.instance();
Entity profileEntity = repo.getEntity(currentUser.getID());
Album album = profileEntity.getAlbum(request.getParameter("album"));

if (request.getParameter("profilePicture").equals("yes"))
{
     Picture picture = album.getPicture(request.getParameter("picture"));
     profileEntity.makeProfilePicture(picture);

     response.sendRedirect("../index.jsp");
}

MultipartRequest mpr = 
     new MultipartRequest(request, "../data/pictures/", 9999999);

String source = "data/pictures/" + mpr.getOriginalFileName("filename");

Picture picture = profileEntity.makePicture(source);
profileEntity.addPicture(picture);
album.addPicture(picture);

response.sendRedirect("../index.jsp"); //Where are we redirecting all this too?

%>
