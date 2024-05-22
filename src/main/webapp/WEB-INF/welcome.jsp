<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>
<!-- Add Bootstrap CSS link -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h1> Welcome <c:out value="${user.userName}"/> !</h1>

    <form action="/logout" method="POST">
        <button type="submit" class="btn btn-danger">Logout</button>
    </form>

    <a href="/projects/new" class="btn btn-primary mt-3">+ New Project</a>

    <h3 class="mt-4">All Projects</h3>

    <table class="table table-striped">
        <thead>
            <tr>
                <th>Project</th>
                <th>Team Lead</th>
                <th>Due Date</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="project" items="${otherProjects}">
                <tr>
                    <td><a href="/projects/${project.id}">${project.title}</a></td>
                    <td>${project.leadUser.getUserName()}</td>
                    <td>${project.dueDate}</td>
                    <td><a href="/projects/${project.id}/join" class="btn btn-success">Join Team</a></td>
                </tr>
             </c:forEach>
        </tbody>
    </table>

    <h3 class="mt-4">Your Projects</h3>

    <table class="table table-striped">
        <thead>
            <tr>
                <th>Project</th>
                <th>Team Lead</th>
                <th>Due Date</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="project" items="${myProjects}">
                <tr>
                    <td><a href="/projects/${project.id}">${project.title}</a></td>
                    <td>${project.leadUser.getUserName()}</td>
                    <td>${project.dueDate}</td>
                    <c:if test="${project.leadUser.id.equals(user.id)}"> 
                        <td><a href="/projects/${project.id}/edit" class="btn btn-primary">Edit</a></td>
                    </c:if>        
                    <c:if test="${!project.leadUser.id.equals(user.id)}"> 
                        <td><a href="/projects/${project.id}/leave" class="btn btn-danger">Leave Team</a></td>
                    </c:if>          
                </tr>
             </c:forEach>
        </tbody>
    </table>
</div>

<!-- Add Bootstrap JS and jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
