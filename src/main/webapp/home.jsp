<%@ page import = "farcebook.*"%>
<%@ page import = "java.util.*"%>
<%
if (session.getAttribute("currentUser") != null){
     User currentUser = (User) session.getAttribute("currentUser");
     Repository repo = Repository.instance();

     if(!currentUser.isVerified()) {
          response.sendRedirect("answerEmail.jsp");
     }

     //User thisUser = repo.getEntity(userID);

     //Get all of the info from the User here
     // (subscribers, groups, information, etc)

     %>
          <!doctype html>
          <html>
          <head>
          <title><%= currentUser.getDisplayName() %> | Farcebook</title>
          <link rel="stylesheet" href="etc/styles/bootstrap.min.css">
          <link rel="stylesheet" href="etc/styles/jquery-ui-1.8.16.custom.css">
          <link rel="stylesheet" href="etc/styles/farcebook.css">

          <link rel="icon" type="image/ico" href="etc/images/favicon.ico"/>

          <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
          <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js"></script>
          <script src="etc/js/bootstrap-tabs.js"></script>

          <script>
          $(function(){
                    $('#datepicker2').datepicker();
                    });
     </script>
          <script>
          $(function(){
                    $('#datepicker').datepicker();
                    });
     </script>

          </head>
          <body>

          <jsp:include page="/views/menu2.jsp"/>
          <div class= "container-fluid">
          <div class="sidebar">
          <div class="photo">
          <img src = "<% String source = currentUser.getProfilePicture().getSource();
                         out.print(source); %>" width=100 height=auto></img>
          <span><h2><%= currentUser.getDisplayName()%></h2></span>
          </div>

          <hr>
          <div id="info">
          <ul>
          <li><a href="?make=event">Create New Event</a></li>
          <li><a href="?make=group">Create New Group</a></li>
          </ul>
          </div> 
          <%   /*
                  <li><a href="#">My Friends</a></li>
                  <li><a href="#">My Groups</a></li>
                  <li><a href="#">My Events</a></li>
                */
          %>
          </div>

          <div class="content"> 
          <%
          String search = request.getParameter("search");
     if(search != null) {
          %>
              <jsp:include page="views/searchResults.jsp"/>

               <% 

     } else {

          String make = request.getParameter("make");
          if(make==null) {
               %>

                    <ul class="tabs">
                    <li class="active"><a href="#feed">Newsfeed</a></li>
                    <li><a href="#friends">Friends
                    <% if(currentUser.getSubscribedTo()!= null){
                         out.print("[" + currentUser.getSubscribedTo().size() + "]");
                    }%></a></li>
               <li><a href="#events">Events
                    <% if(currentUser.getEvents() != null){
                         out.print("[" + currentUser.getEvents().size() + "]");
                    }%></a></li>
               <li><a href="#groups">Groups
                    <% if(currentUser.getGroups() != null){
                         out.print("[" + currentUser.getGroups().size() + "]");
                    }%></a></li>
               <li><a href="#requests">Requests
                    <% if(currentUser.getRequests() != null){
                         out.print("[" + currentUser.getRequests().size() + "]");
                    }%>
               </a></li>
                    
                    <% /*
                    //Comment back in when picture uploading works
                    <li><a href="#albums">Albums</a></li>
                    */ %>
                    
                    </ul>
                    <div class="pill-content">
                    <div class="active" id="feed">
                    <jsp:include page = "views/feed.jsp"/>
                    </div>

                    <div id="friends">
                    <jsp:include page = "views/entityList.jsp?type=user"/>
                    </div>

                    <div id="events">
                    <jsp:include page = "views/entityList.jsp?type=event"/>
                    </div>

                    <div id="groups">
                    <jsp:include page = "views/entityList.jsp?type=group"/>
                    </div>

                    <% /*
                    //Comment back in when picture uploading works
                    <div id="albums">
                    <jsp:include page = "views/albums.jsp" />
                    </div>
                    */%>
                    
                    <div id="requests">
                    <%
                    //ArrayList<Post> tmp = currentUser.get
                    if(currentUser.getRequests() != null){
                         if(currentUser.getRequests().size() == 0){
                              out.print("You don't have any pending requests. Why don't you go out and try to make some friends?");
                         }else{
                              for(int i=0; i<currentUser.getRequests().size(); i++){
                                   out.print(" " + currentUser.getRequests().get(i).getText());
                                   out.print("<form action=controllers/friendController.jsp method=get>");
                                   out.print("<input type=hidden name=newFriend value=" + i + " ></input>");
                                   out.print("<input type =submit name=submit value=Accept ></input>");
                                   out.print("</form>");
                              }
                         }
                    }
               %>
                    </div>



                    <%
          } else if(make.equals("group")) {

               %>
                    <div class="group offset-two-thirds span4">
                    <jsp:include page="views/makeGroup.jsp"/>
                    </div>
                    <% } else if(make.equals("event")) {
                         %>
                              <div class="event offset-two-thirds span4">
                              <jsp:include page="views/makeEvent.jsp"/>
                              </div>
                              <%
              }      
          else if (request.getParameter("privacy").equals("1")){
               %>  <jsp:include page="views/privacy.jsp"/>  <%
          }
     }         

     %>
          </div>
          </div>
          </body>
          </html>


          <script>
          $(function () {
                    $('.tabs').tabs()
                    })
     </script>

          <%
}

else {
     response.sendRedirect("index.jsp");
}
%>   
