<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Read Share</title>
</head>
<body>
<h1>Project Details</h1>

<form action="/logout" method="POST">
	<button type="submit">Logout</button>
</form>

<a href="/dashboard">back to dashboard</a>


	<p>Project: ${project.title}</p>
	<p>Description: ${project.description}</p>
	<p>Due Date: ${project.dueDate}</p>
	
	<a href="/projects/${project.id}/tasks">see Tasks!</a>
	
	
</body>
</html>