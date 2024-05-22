<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Book Share</title>
</head>
<body>
<h1>Create a Project</h1>	    

	<a href="/dashboard">back to Dashboard</a>
	
	<form action="/logout" method="POST">
		<button type="submit">Logout</button>
	</form>

	<form:form action="/projects/new" method="post" modelAttribute="newProject">

	    <p>
	        <form:label path="title">Project Title:</form:label>
	        <form:errors path="title"/>
	        <form:input path="title"/>
	    </p>
	    <p>
	        <form:label path="description">Description:</form:label>
	        <form:errors path="description"/>
	        <form:input path="description"/>
	    </p>
	    <p>
	        <form:label path="dueDate">Due Date:</form:label>
	        <form:errors path="dueDate"/>
	        <form:input type="date" path="dueDate" pattern="yyyy-MM-dd"/>
	    </p>
	    
	    <form:hidden path="leadUser" value="${user.id}" />
	    
	    <input type="submit"/>
	    
	</form:form> 
	

</body>
</html>