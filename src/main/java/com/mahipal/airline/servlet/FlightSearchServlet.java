package com.mahipal.airline.servlet;

import com.mahipal.airline.dao.FlightDAO;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/search")
public class FlightSearchServlet extends HttpServlet {
    private final FlightDAO flightDAO = new FlightDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String origin = req.getParameter("origin");
        String destination = req.getParameter("destination");
        String date = req.getParameter("date");

        if (origin == null || destination == null || date == null ||
            origin.isBlank() || destination.isBlank() || date.isBlank()) {
            res.sendRedirect("index.jsp?error=Enter+origin%2C+destination+and+date");
            return;
        }

        try {
            req.setAttribute("flights", flightDAO.search(origin, destination, date));
            req.setAttribute("origin", origin);
            req.setAttribute("destination", destination);
            req.setAttribute("date", date);
            req.getRequestDispatcher("search-results.jsp").forward(req, res);
        } catch (Exception e) {
            throw new ServletException("Flight search failed.", e);
        }
    }
}
