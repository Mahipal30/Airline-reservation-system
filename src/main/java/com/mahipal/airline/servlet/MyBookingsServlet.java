package com.mahipal.airline.servlet;

import com.mahipal.airline.dao.BookingDAO;
import com.mahipal.airline.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/my-bookings")
public class MyBookingsServlet extends HttpServlet {
    private final BookingDAO bookingDAO = new BookingDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        try {
            req.setAttribute("bookings", bookingDAO.findByUser(user.getId()));
            req.getRequestDispatcher("my-bookings.jsp").forward(req, res);
        } catch (Exception e) {
            throw new ServletException("Could not load bookings.", e);
        }
    }
}
