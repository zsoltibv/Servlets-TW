package com.example.servletexample.servlets;

import com.example.servletexample.model.User;
import com.example.servletexample.Repository.UserRepository;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/register/*")
public class RegisterServlet extends HttpServlet {
    private final UserRepository userRepository;
    public RegisterServlet(){
        this.userRepository = new UserRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DO GET REGISTER");
        getServletContext().getRequestDispatcher("/jsp/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("REGISTER " + email + " + " + password);
        User user = new User();
        user.setEmail(email);
        user.setPass(password);

        HttpSession session = request.getSession();
        session.setAttribute("currentUser", user);
        userRepository.addUser(user);

        getServletContext().getRequestDispatcher("/login").forward(request, response);
    }
}
