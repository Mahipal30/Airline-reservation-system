package com.mahipal.airline.servlet;

import com.mahipal.airline.dao.FlightDAO;
import com.mahipal.airline.model.Flight;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;

@WebServlet("/admin/flights")
public class AdminFlightServlet extends HttpServlet {
    private final FlightDAO flightDAO = new FlightDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            req.setAttribute("flights", flightDAO.findAll());
            req.getRequestDispatcher("/admin-flights.jsp").forward(req, res);
        } catch (Exception e) {
            throw new ServletException("Could not load flights.", e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            Flight f = new Flight();
            f.setFlightNumber(req.getParameter("flightNumber"));
            f.setAirline(req.getParameter("airline"));
            f.setOrigin(req.getParameter("origin"));
            f.setDestination(req.getParameter("destination"));
            f.setDepartureTime(Timestamp.valueOf(req.getParameter("departureTime").replace("T", " ") + ":00"));
            f.setArrivalTime(Timestamp.valueOf(req.getParameter("arrivalTime").replace("T", " ") + ":00"));
            f.setPrice(new BigDecimal(req.getParameter("price")));
            f.setTotalSeats(Integer.parseInt(req.getParameter("totalSeats")));
            flightDAO.create(f);
            res.sendRedirect(req.getContextPath() + "/admin/flights?message=Flight+added");
        } catch (Exception e) {
            throw new ServletException("Could not create flight.", e);
        }
    }
}
