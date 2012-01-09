	<div>
		<a href="index.jsp"><img src = "http://jamsidedown.com/images/2007-small/10/21/farcebook2_logo.jpg"></a>
			<%
                    if(session.getAttribute("currentUser")==null) {

               %>
               <jsp:include page="login.jsp"/>
               <%
                    }
               %>
	</div>
