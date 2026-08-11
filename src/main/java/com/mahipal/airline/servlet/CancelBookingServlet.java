package com.mahipal.airline.servlet;

import com.mahipal.airline.dao.BookingDAO;
import com.mahipal.airline.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/cancel-booking")
public class CancelBookingServlet extends HttpServlet {
    private final BookingDAO bookingDAO = new BookingDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        try {
            int bookingId = Integer.parseInt(req.getParameter("bookingId"));
            bookingDAO.cancel(bookingId, user.getId());
            res.sendRedirect(req.getContextPath() + "/my-bookings?message=Booking+cancelled");
        } catch (Exception e) {
            throw new ServletException("Cancellation failed.", e);
        }
    }
}
