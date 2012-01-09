<%@ page import="farcebook.*"%>

<%
User currentUser = (User) session.getAttribute("currentUser");
%>

<div class="topbar-wrapper" style="z-index: 5;">
<div class="topbar">
     <div class="topbar-inner">
     <div class="container">
          
     <span class="brand"><a href="index.jsp"> <img src = "etc/images/farcelogo.jpg"></a></span>
    
     <ul class="nav">
		<li><a href= "profile.jsp?ID=<%=currentUser.getID()%>">My Profile</a></li>
          <li><a href= "index.jsp">Home</a></li>
          <li><form action = "controllers/searchController.jsp" method = "GET">
		     <input type = text size = 25 name = "search">
		     <input type = submit name = submit value = "Search">
          </form></li>
		<li><a href="controllers/logout.jsp">Logout</a></li>
     </ul>
     
     <ul class="nav secondary-nav">
               <a href="updatedUserLicenseAgreement.jsp" class="toggle">End-User License Agreement</a>
     </ul>

     </div>
     </div>
</div>
</div>
