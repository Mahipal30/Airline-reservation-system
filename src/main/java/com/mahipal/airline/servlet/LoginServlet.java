package com.mahipal.airline.servlet;

import com.mahipal.airline.dao.UserDAO;
import com.mahipal.airline.model.User;
import com.mahipal.airline.util.PasswordUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            User user = userDAO.findByEmail(email == null ? "" : email.trim().toLowerCase());
            if (user != null && PasswordUtil.matches(password == null ? "" : password, user.getPasswordHash())) {
                req.getSession().setAttribute("user", user);
                res.sendRedirect(req.getContextPath() + "/");
            } else {
                res.sendRedirect("login.jsp?error=Invalid+email+or+password");
            }
        } catch (Exception e) {
            throw new ServletException("Login failed.", e);
        }
    }
}
