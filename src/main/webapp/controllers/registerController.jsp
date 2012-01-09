<%@ page import = "java.util.*" %>
<%@ page import = "farcebook.*" %>

<%

if(request.getParameter("submit") == null){
	response.sendRedirect("../index.jsp");
}

Repository repo = Repository.instance();
String email = request.getParameter("userEmail");
String pass1 = request.getParameter("password");
String pass2 = request.getParameter("password2");
String firstName = request.getParameter("firstName");
String lastName = request.getParameter("lastName");
String address = request.getParameter("address");
String alias = request.getParameter("alias");
String ssn1 = request.getParameter("ssn1");
String ssn2 = request.getParameter("ssn2");
String ssn3 = request.getParameter("ssn3");
String maidenName = request.getParameter("maidenName");
String income = request.getParameter("income");


if(email.equals("") || pass1.equals("")  || pass2.equals("")) {
     response.sendRedirect("../index.jsp?empty=1");
} 
if (firstName.equals(null) || lastName.equals(null) || address.equals(null) || 
     alias.equals(null) || ssn1.equals(null) || ssn2.equals(null) || 
     ssn3.equals(null) || ssn1.length() != 3 || ssn2.length() != 2 || 
     ssn3.length() != 4 || maidenName.equals(null) || income.equals(null)) {
     response.sendRedirect("../index.jsp?empty-1");
}

boolean goodEmail = false;
if (email.contains("@") == true){
	String validEmail[] = email.split("@");
	if (validEmail[1].contains(".")) {
         goodEmail = true; 
     }
}

if(goodEmail==false){
	response.sendRedirect("../index.jsp?badEmail=1");
}

else {
     User currentUser = repo.createNewUser(email, pass1);
     if(currentUser != null) {
		currentUser.setAttribute("firstName", firstName);
          currentUser.setAttribute("lastName", lastName);
          currentUser.setAttribute("address", address);
          currentUser.setAttribute("alias", alias);
          currentUser.setAttribute("SSN", ssn1 + ssn2 + ssn3);
          currentUser.setAttribute("MMN", maidenName);
          currentUser.setAttribute("income", income);
          currentUser.assembleDisplayName();
          session.setAttribute("currentUser", currentUser);
		response.sendRedirect("../home.jsp");
	}
     else {
          response.sendRedirect("../index.jsp?taken=1");
     }
}


%>
