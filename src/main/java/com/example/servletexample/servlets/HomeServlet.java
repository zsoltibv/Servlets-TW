package com.example.servletexample.servlets;

import com.example.servletexample.Repository.DisciplineRepository;
import com.example.servletexample.Repository.UserRepository;
import com.example.servletexample.enums.Role;
import com.example.servletexample.model.Discipline;
import com.example.servletexample.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "HomeServlet", value = "/homeServlet")
public class HomeServlet extends HttpServlet {
    private final UserRepository userRepository;

    public HomeServlet() throws SQLException, ClassNotFoundException {
        this.userRepository = new UserRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

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
                List<Discipline> teacherDisciplines = disciplineRepository.getDisciplinesByProfessor(teacherId);
                System.out.println(teacherDisciplines);

                request.setAttribute("teacherDisciplines", teacherDisciplines);
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
}

