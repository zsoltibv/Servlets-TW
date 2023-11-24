<%@ page import="com.example.servletexample.model.Discipline" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.servletexample.model.User" %>
<%@ page import="com.example.servletexample.enums.Role" %>
<%@ page import="com.example.servletexample.model.Grade" %><%--
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
    <div class="w-screen h-screen flex justify-center items-center
            bg-gradient-to-br from-purple-700 to-amber-700">
        <div class="p-10 bg-white rounded-xl drop-shadow-lg space-y-5">

            <%-- Check if the user is a teacher to display the list of disciplines --%>
            <% if (session.getAttribute("currentUser") != null && ((User) session.getAttribute("currentUser")).getRole() == Role.TEACHER) { %>
            <h2>Your Disciplines:</h2>
            <ul>
                <% for (Discipline discipline : (List<Discipline>) request.getAttribute("teacherDisciplines")) { %>
                <li><%= discipline.getName() %>
                </li>
                <% } %>
            </ul>
            <% } %>

            <%-- Form to give a grade --%>
            <%-- Form to give a grade --%>
            <form action="/homeServlet" method="POST">
                <input type="hidden" name="action" value="giveGrade">
                <label>Select Discipline:</label>
                <select name="disciplineId">
                    <% for (Discipline discipline : (List<Discipline>) request.getAttribute("teacherDisciplines")) { %>
                    <option value="<%= discipline.getId() %>"><%= discipline.getName() %>
                    </option>
                    <% } %>
                </select>
                <br>
                <labe>Select Student:</labe>
                <select name="studentId">
                    <% for (User student : (List<User>) request.getAttribute("students")) { %>
                    <option value="<%= student.getId() %>"><%= student.getEmail() %>
                    </option>
                    <% } %>
                </select>
                <br>
                <label>Enter Grade:</label>
                <input type="text" name="value">
                <br>
                <button type="submit">Give Grade</button>
            </form>

        </div>
    </div>


</div>
</body>
</html>
