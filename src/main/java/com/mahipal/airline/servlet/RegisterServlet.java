package com.mahipal.airline.servlet;

import com.mahipal.airline.dao.UserDAO;
import com.mahipal.airline.model.User;
import com.mahipal.airline.util.PasswordUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String name = req.getParameter("fullName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (name == null || email == null || password == null ||
            name.isBlank() || email.isBlank() || password.length() < 6) {
            res.sendRedirect("register.jsp?error=Please+enter+valid+details");
            return;
        }

        try {
            if (userDAO.findByEmail(email.trim()) != null) {
                res.sendRedirect("register.jsp?error=Email+already+registered");
                return;
            }

            User user = new User();
            user.setFullName(name.trim());
            user.setEmail(email.trim().toLowerCase());
            user.setPasswordHash(PasswordUtil.hash(password));
            userDAO.create(user);

            res.sendRedirect("login.jsp?message=Registration+successful");
        } catch (Exception e) {
            throw new ServletException("Registration failed.", e);
        }
    }
}
