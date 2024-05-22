<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>      
<%@ page isErrorPage="true" %>  
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

 
<h1>Edit Project:</h1>
<a href="/dashboard">back to dashboard</a>

<form:form action="/projects/${project.id}/edit" method="post" modelAttribute="project">
    	<input type="hidden" name="_method" value="put">
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
	    
	   	<form:hidden path="id"  />
	    <form:hidden path="leadUser"  />
	   	<form:hidden path="memberUsers"/>
	    
	    
    <input type="submit" value="Update"/>
</form:form>  


</body>
</html>