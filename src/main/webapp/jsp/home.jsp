<%@ page import="com.example.servletexample.model.Discipline" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.servletexample.model.User" %>
<%@ page import="com.example.servletexample.enums.Role" %><%--
  Created by IntelliJ IDEA.
  User: Ritan
  Date: 10/15/2022
  Time: 11:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
</head>

<body>
<div>
    <h1>shit</h1>
    <%-- Check if the user is a teacher to display the list of disciplines --%>
    <% if (session.getAttribute("currentUser") != null && ((User) session.getAttribute("currentUser")).getRole() == Role.TEACHER) { %>
    <h2>Your Disciplines:</h2>
    <ul>
        <% for (Discipline discipline : (List<Discipline>) request.getAttribute("teacherDisciplines")) { %>
        <li><%= discipline.getName() %></li>
        <% } %>
    </ul>
    <% } %>
</div>
</body>
</html>
