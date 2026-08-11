package com.mahipal.airline.dao;

import com.mahipal.airline.model.Flight;
import com.mahipal.airline.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO {

    public List<Flight> search(String origin, String destination, String date) throws SQLException {
        String sql = """
            SELECT f.*,
                   f.total_seats - COALESCE(SUM(CASE WHEN b.status='CONFIRMED' THEN 1 ELSE 0 END),0) AS available_seats
            FROM flights f
            LEFT JOIN bookings b ON f.id=b.flight_id
            WHERE LOWER(f.origin)=LOWER(?)
              AND LOWER(f.destination)=LOWER(?)
              AND DATE(f.departure_time)=?
            GROUP BY f.id
            ORDER BY f.departure_time
            """;

        List<Flight> flights = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, origin.trim());
            ps.setString(2, destination.trim());
            ps.setDate(3, Date.valueOf(date));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    flights.add(map(rs));
                }
            }
        }
        return flights;
    }

    public Flight findById(int id) throws SQLException {
        String sql = """
            SELECT f.*,
                   f.total_seats - COALESCE(SUM(CASE WHEN b.status='CONFIRMED' THEN 1 ELSE 0 END),0) AS available_seats
            FROM flights f
            LEFT JOIN bookings b ON f.id=b.flight_id
            WHERE f.id=?
            GROUP BY f.id
            """;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public List<Flight> findAll() throws SQLException {
        String sql = """
            SELECT f.*,
                   f.total_seats - COALESCE(SUM(CASE WHEN b.status='CONFIRMED' THEN 1 ELSE 0 END),0) AS available_seats
            FROM flights f
            LEFT JOIN bookings b ON f.id=b.flight_id
            GROUP BY f.id
            ORDER BY f.departure_time
            """;
        List<Flight> flights = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) flights.add(map(rs));
        }
        return flights;
    }

    public boolean create(Flight f) throws SQLException {
        String sql = """
            INSERT INTO flights(flight_number,airline,origin,destination,
                                 departure_time,arrival_time,price,total_seats)
            VALUES(?,?,?,?,?,?,?,?)
            """;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, f.getFlightNumber());
            ps.setString(2, f.getAirline());
            ps.setString(3, f.getOrigin());
            ps.setString(4, f.getDestination());
            ps.setTimestamp(5, f.getDepartureTime());
            ps.setTimestamp(6, f.getArrivalTime());
            ps.setBigDecimal(7, f.getPrice());
            ps.setInt(8, f.getTotalSeats());
            return ps.executeUpdate() == 1;
        }
    }

    private Flight map(ResultSet rs) throws SQLException {
        Flight f = new Flight();
        f.setId(rs.getInt("id"));
        f.setFlightNumber(rs.getString("flight_number"));
        f.setAirline(rs.getString("airline"));
        f.setOrigin(rs.getString("origin"));
        f.setDestination(rs.getString("destination"));
        f.setDepartureTime(rs.getTimestamp("departure_time"));
        f.setArrivalTime(rs.getTimestamp("arrival_time"));
        f.setPrice(rs.getBigDecimal("price"));
        f.setTotalSeats(rs.getInt("total_seats"));
        f.setAvailableSeats(rs.getInt("available_seats"));
        return f;
    }
}
