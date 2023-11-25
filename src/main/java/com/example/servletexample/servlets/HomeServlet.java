package com.example.servletexample.servlets;

import com.example.servletexample.Repository.DisciplineRepository;
import com.example.servletexample.Repository.GradeRepository;
import com.example.servletexample.Repository.UserRepository;
import com.example.servletexample.enums.Role;
import com.example.servletexample.model.Discipline;
import com.example.servletexample.model.Grade;
import com.example.servletexample.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "HomeServlet", value = "/homeServlet/*")
public class HomeServlet extends HttpServlet {
    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;
    private final DisciplineRepository disciplineRepository;
    List<Discipline> teacherDisciplines;
    List<User> students;

    public HomeServlet() throws SQLException, ClassNotFoundException {
        this.gradeRepository = new GradeRepository();
        this.userRepository = new UserRepository();
        this.disciplineRepository = new DisciplineRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        students = UserRepository.getStudents();

        System.out.println(currentUser);

        if (currentUser != null) {
            if (currentUser.getRole() == Role.TEACHER) {
                // The user is a teacher, retrieve and display the list of disciplines
                int teacherId = currentUser.getId(); // Assuming you have the teacher's ID stored in the User object

                DisciplineRepository disciplineRepository = null;
                try {
                    disciplineRepository = new DisciplineRepository();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                teacherDisciplines = disciplineRepository.getDisciplinesByProfessor(teacherId);
                System.out.println(teacherDisciplines);

                request.setAttribute("teacherDisciplines", teacherDisciplines);
                request.setAttribute("students", students);
                getServletContext().getRequestDispatcher("/jsp/home.jsp").forward(request, response);
            } else {
                // The user is a student, you can add student-specific logic or leave it empty
                getServletContext().getRequestDispatcher("/jsp/home.jsp").forward(request, response);
            }
        } else {
            // Handle the case where the user is not logged in
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("giveGrade".equals(action)) {
            giveGrade(request, response);
        } else if ("showGrades".equals(request.getParameter("action"))) {
            System.out.println("show grades");
            showGrades(request, response);
        }
    }

    private void giveGrade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int disciplineId = Integer.parseInt(request.getParameter("disciplineId"));
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        int value = Integer.parseInt(request.getParameter("value"));

        // You may want to perform validation on the inputs before proceeding

        // Create a Grade object with the submitted data
        Grade grade = new Grade();
        grade.setDisciplineId(disciplineId);
        grade.setStudentId(studentId);
        grade.setValue(value);

        // Save the grade to the database
        gradeRepository.addGrade(grade);

        response.sendRedirect(request.getContextPath() + "/homeServlet");
    }

    private void showGrades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve selected student and discipline IDs
        int selectedStudentId = Integer.parseInt(request.getParameter("studentId"));
        int selectedDisciplineId = Integer.parseInt(request.getParameter("disciplineId"));

        // Retrieve grades based on the selected student and discipline
        List<Grade> grades = gradeRepository.getGradesByStudentAndDiscipline(selectedStudentId, selectedDisciplineId);

        System.out.println(grades);

        // Set the grades attribute in the request
        request.setAttribute("grades", grades);
        request.setAttribute("teacherDisciplines", teacherDisciplines);
        request.setAttribute("students", students);
        request.setAttribute("disciplineName", disciplineRepository.getDisciplineName(selectedDisciplineId));

        // Forward to the same JSP page
        request.getRequestDispatcher("/jsp/home.jsp").forward(request, response);
    }
}

