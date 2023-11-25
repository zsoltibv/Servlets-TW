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
        <!-- Logout Button -->
        <form action="/logout" name="action" value="logout" method="POST">
            <button type="submit"
                    class="absolute top-4 right-4 px-4 py-2 text-white bg-red-500 rounded-md hover:bg-red-600 focus:outline-none focus:ring focus:border-red-300 active:bg-red-800">
                Logout
            </button>
        </form>

        <div class="p-10 bg-white rounded-xl drop-shadow-lg space-y-5 w-96">

            <%-- Check if the user is a teacher to display the list of disciplines --%>
            <% if (session.getAttribute("currentUser") != null) {
                User currentUser = (User) session.getAttribute("currentUser");
                if (currentUser.getRole() == Role.TEACHER) { %>
            <h2 class="text-2xl font-bold mb-4">Your Disciplines:</h2>
            <ul class="list-disc pl-4">
                <% for (Discipline discipline : (List<Discipline>) request.getAttribute("teacherDisciplines")) { %>
                <li class="mb-2"><%= discipline.getName() %>
                </li>
                <% } %>
            </ul>
            <form action="/homeServlet" method="POST" class="mt-8 space-y-4">
                <!-- Select Discipline -->
                <div>
                    <label for="disciplineId" class="block text-sm font-medium text-gray-700">Select Discipline:</label>
                    <select name="disciplineId" id="disciplineId"
                            class="mt-1 block w-full p-2 border border-gray-300 rounded-md">
                        <% for (Discipline discipline : (List<Discipline>) request.getAttribute("teacherDisciplines")) { %>
                        <option value="<%= discipline.getId() %>"><%= discipline.getName() %>
                        </option>
                        <% } %>
                    </select>
                </div>
                <!-- Select Student -->
                <div>
                    <label for="studentId" class="block text-sm font-medium text-gray-700">Select Student:</label>
                    <select name="studentId" id="studentId"
                            class="mt-1 block w-full p-2 border border-gray-300 rounded-md">
                        <% for (User student : (List<User>) request.getAttribute("students")) { %>
                        <option value="<%= student.getId() %>"><%= student.getEmail() %>
                        </option>
                        <% } %>
                    </select>
                </div>
                <!-- Enter Grade -->
                <div>
                    <label for="value" class="block text-sm font-medium text-gray-700">Enter Grade:</label>
                    <input type="text" name="value" id="value"
                           class="mt-1 p-2 border border-gray-300 rounded-md w-full">
                </div>
                <!-- Submit Button -->
                <div>
                    <button type="submit" name="action" value="giveGrade"
                            class="px-4 py-2 text-white bg-indigo-500 rounded-md hover:bg-indigo-600 focus:outline-none focus:ring focus:border-indigo-300 active:bg-indigo-800">
                        Give Grade
                    </button>
                    <button type="submit" name="action" value="showGrades"
                            class="px-4 py-2 mt-4 text-white bg-blue-500 rounded-md hover:bg-blue-600 focus:outline-none focus:ring focus:border-blue-300 active:bg-blue-800">
                        Show Grades
                    </button>
                </div>
            </form>
            <% } else if (currentUser.getRole() == Role.STUDENT) { %>
            <h2 class="text-2xl font-bold mb-4">Your Grades:</h2>
            <div class="mt-4">
                <%-- Check if grades are available in the request and display them --%>
                <% List<Grade> grades = (List<Grade>) request.getAttribute("studentGrades");
                    if (grades != null && !grades.isEmpty()) { %>
                <h3>Grades:</h3>
                <ul>
                    <% for (Grade grade : grades) { %>
                    <li>Discipline: <%= grade.getDisciplineId() %>, Grade: <%= grade.getValue() %>
                    </li>
                    <% } %>
                </ul>
                <% } else { %>
                <p>No grades available for the selected student and discipline.</p>
                <% } %>
            </div>
            <% }
            } %>

        </div>
    </div>


</div>
</body>
</html>
