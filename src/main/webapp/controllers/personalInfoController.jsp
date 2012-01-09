<%@ page import = "java.util.*"%>
<%@ page import = "farcebook.*" %>

<%
User currentUser = (User)session.getAttribute("currentUser"); 
if(currentUser==null || request.getParameter("submit") == null) {
     response.sendRedirect("../personalInfo.jsp");
}
else { 

     String firstName = request.getParameter("firstName");
     String lastName = request.getParameter("lastName");
     String address = request.getParameter("address");
     String alias = request.getParameter("alias");
     String ssn1 = request.getParameter("ssn1");
     String ssn2 = request.getParameter("ssn2");
     String ssn3 = request.getParameter("ssn3");
     String maidenName = request.getParameter("maidenName");
     String income = request.getParameter("income");


     if (firstName.equals(null) || lastName.equals(null) || address.equals(null) || 
         alias.equals(null) || ssn1.equals(null) || ssn2.equals(null) || 
         ssn3.equals(null) || ssn1.length() != 3 || ssn2.length() != 2 || 
         ssn3.length() != 4 || maidenName.equals(null) || income.equals(null)) {
          response.sendRedirect("../personalInfo.jsp?badInfo=1");
     }
     else {
    
          currentUser.setAttribute("firstName", firstName);
          currentUser.setAttribute("lastName", lastName);
          currentUser.setAttribute("address", address);
          currentUser.setAttribute("alias", alias);
          currentUser.setAttribute("SSN", ssn1 + ssn2 + ssn3);
          currentUser.setAttribute("MMN", maidenName);
          currentUser.setAttribute("income", income);
          currentUser.assembleDisplayName();
          response.sendRedirect("../home.jsp");
     }
}
%>
