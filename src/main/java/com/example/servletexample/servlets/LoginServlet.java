package com.example.servletexample.servlets;

import com.example.servletexample.model.User;
import com.example.servletexample.Repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

@WebServlet(name = "IndexServlet", urlPatterns = {"/","/login/*"})
public class LoginServlet extends HttpServlet {
    private final UserRepository userRepository;
    public LoginServlet() throws SQLException, ClassNotFoundException {
        this.userRepository = new UserRepository();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Check if user is already connected, if not show login page */
        System.out.println("GET   LOGIN");
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        System.out.println("USER " + currentUser);

        System.out.println("ALL USERS");
        userRepository.getUsers().forEach(System.out::println);
        /* If not logged */
        if(Objects.isNull(currentUser)) {
            getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        }
        else {
            response.sendRedirect(request.getContextPath() + "/homeServlet");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Check has the right credentials and use HomeServlet */
        userRepository.getUsers().forEach(System.out::println);

        HttpSession session = request.getSession();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Optional<User> userByEmailAndPassword = userRepository.findUserByEmailAndPassword(email, password);
        if (userByEmailAndPassword.isPresent()) {
            // Set the current user in the session
            User currentUser = userByEmailAndPassword.get();
            session.setAttribute("currentUser", currentUser);

            // Redirect to HomeServlet after successful login
            response.sendRedirect(request.getContextPath() + "/homeServlet");
        } else {
            // Otherwise, reload the login form
            getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        }
    }
}