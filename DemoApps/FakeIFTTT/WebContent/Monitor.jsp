<%@page import="org.internetrt.demo.fakeifttt.Listener"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta   http-equiv="refresh"   content="5">
<title>Ifttt</title>
</head>
<body bgcolor="#cc3000">
<p>Events happening now</p>
<hr color="#cc000">
<p>If there is some event happening, the OS will feel the signal,
and tell us who is doing this action and what is happening.</p>
<p>For example,if you are picking a picture, the OS will know your action,
and it will describe your username and your action below!</p>
<%=Listener.getResponse(Listener.checkUser(request))%>
</body>
</html>