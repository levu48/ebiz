<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h2>PT-eBiz Test</h2>
<%

	UserService userService = UserServiceFactory.getUserService();
	com.google.appengine.api.users.User user = userService.getCurrentUser();
	%><a href="/">home</a><br><% 
	if (user != null) {
		out.println(user.getEmail());
		%>, <a href="<%=userService.createLogoutURL("/test/login.jsp")%>">logout</a> <%
	} else {
		%><a href="<%=userService.createLoginURL("/test/login.jsp")%>">login</a>, <%
		out.println("<br>has not logged in.");
	}
	
%>
<br>Done.
</body>
</html>