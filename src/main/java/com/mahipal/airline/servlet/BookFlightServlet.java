package com.mahipal.airline.servlet;

import com.mahipal.airline.dao.BookingDAO;
import com.mahipal.airline.dao.FlightDAO;
import com.mahipal.airline.model.Booking;
import com.mahipal.airline.model.Flight;
import com.mahipal.airline.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/book")
public class BookFlightServlet extends HttpServlet {
    private final FlightDAO flightDAO = new FlightDAO();
    private final BookingDAO bookingDAO = new BookingDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            int flightId = Integer.parseInt(req.getParameter("flightId"));
            Flight flight = flightDAO.findById(flightId);
            if (flight == null) {
                res.sendError(404, "Flight not found.");
                return;
            }
            req.setAttribute("flight", flight);
            req.getRequestDispatcher("book.jsp").forward(req, res);
        } catch (Exception e) {
            throw new ServletException("Could not load flight.", e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        try {
            Booking b = new Booking();
            b.setUserId(user.getId());
            b.setFlightId(Integer.parseInt(req.getParameter("flightId")));
            b.setPassengerName(req.getParameter("passengerName"));
            b.setPassengerEmail(req.getParameter("passengerEmail"));
            b.setPassengerPhone(req.getParameter("passengerPhone"));
            b.setSeatNumber(req.getParameter("seatNumber"));

            if (b.getPassengerName() == null || b.getPassengerName().isBlank() ||
                b.getPassengerEmail() == null || b.getPassengerEmail().isBlank() ||
                b.getPassengerPhone() == null || b.getPassengerPhone().isBlank() ||
                b.getSeatNumber() == null || b.getSeatNumber().isBlank()) {
                res.sendRedirect(req.getContextPath() + "/book?flightId=" + b.getFlightId()
                        + "&error=All+fields+are+required");
                return;
            }

            Booking saved = bookingDAO.createBooking(b);
            req.setAttribute("booking", saved);
            req.setAttribute("flight", flightDAO.findById(b.getFlightId()));
            req.getRequestDispatcher("my-bookings.jsp").forward(req, res);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            try {
                int flightId = Integer.parseInt(req.getParameter("flightId"));
                req.setAttribute("flight", flightDAO.findById(flightId));
            } catch (Exception ignored) {}
            req.getRequestDispatcher("book.jsp").forward(req, res);
        }
    }
}
