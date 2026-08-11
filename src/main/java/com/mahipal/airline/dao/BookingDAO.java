package com.mahipal.airline.dao;

import com.mahipal.airline.model.Booking;
import com.mahipal.airline.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingDAO {

    public Booking createBooking(Booking b) throws SQLException {
        String reference = generateReference();
        String sql = """
            INSERT INTO bookings
            (booking_reference,user_id,flight_id,passenger_name,passenger_email,
             passenger_phone,seat_number,status,payment_status)
            VALUES(?,?,?,?,?,?,?,'CONFIRMED','PENDING')
            """;

        try (Connection c = DBConnection.getConnection()) {
            c.setAutoCommit(false);

            // Lock the selected flight row for this transaction.
            String flightSql = "SELECT id,total_seats FROM flights WHERE id=? FOR UPDATE";
            try (PreparedStatement fps = c.prepareStatement(flightSql)) {
                fps.setInt(1, b.getFlightId());
                try (ResultSet rs = fps.executeQuery()) {
                    if (!rs.next()) throw new SQLException("Flight not found.");
                }
            }

            // The unique constraint on (flight_id, seat_number) is the final
            // protection against concurrent duplicate reservations.
            String seatSql = """
                SELECT id FROM bookings
                WHERE flight_id=? AND seat_number=? AND status='CONFIRMED'
                FOR UPDATE
                """;
            try (PreparedStatement sps = c.prepareStatement(seatSql)) {
                sps.setInt(1, b.getFlightId());
                sps.setString(2, b.getSeatNumber());
                try (ResultSet rs = sps.executeQuery()) {
                    if (rs.next()) {
                        c.rollback();
                        throw new SQLException("Seat " + b.getSeatNumber() + " is already booked.");
                    }
                }
            }

            try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, reference);
                ps.setInt(2, b.getUserId());
                ps.setInt(3, b.getFlightId());
                ps.setString(4, b.getPassengerName());
                ps.setString(5, b.getPassengerEmail());
                ps.setString(6, b.getPassengerPhone());
                ps.setString(7, b.getSeatNumber());
                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) b.setId(keys.getInt(1));
                }
            }

            c.commit();
            b.setBookingReference(reference);
            b.setStatus("CONFIRMED");
            b.setPaymentStatus("PENDING");
            return b;
        }
    }

    public List<Booking> findByUser(int userId) throws SQLException {
        String sql = """
            SELECT b.*, f.flight_number, f.airline, f.origin, f.destination,
                   f.departure_time, f.arrival_time, f.price
            FROM bookings b
            JOIN flights f ON f.id=b.flight_id
            WHERE b.user_id=?
            ORDER BY b.booked_at DESC
            """;
        List<Booking> result = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) result.add(map(rs));
            }
        }
        return result;
    }

    public boolean cancel(int bookingId, int userId) throws SQLException {
        String sql = """
            UPDATE bookings
            SET status='CANCELLED', payment_status='REFUNDED', seat_number=NULL
            WHERE id=? AND user_id=? AND status='CONFIRMED'
            """;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ps.setInt(2, userId);
            return ps.executeUpdate() == 1;
        }
    }

    private String generateReference() {
        return "AIR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private Booking map(ResultSet rs) throws SQLException {
        Booking b = new Booking();
        b.setId(rs.getInt("id"));
        b.setBookingReference(rs.getString("booking_reference"));
        b.setUserId(rs.getInt("user_id"));
        b.setFlightId(rs.getInt("flight_id"));
        b.setPassengerName(rs.getString("passenger_name"));
        b.setPassengerEmail(rs.getString("passenger_email"));
        b.setPassengerPhone(rs.getString("passenger_phone"));
        b.setSeatNumber(rs.getString("seat_number"));
        b.setStatus(rs.getString("status"));
        b.setPaymentStatus(rs.getString("payment_status"));
        b.setBookedAt(rs.getTimestamp("booked_at"));
        b.setFlightNumber(rs.getString("flight_number"));
        b.setAirline(rs.getString("airline"));
        b.setOrigin(rs.getString("origin"));
        b.setDestination(rs.getString("destination"));
        b.setDepartureTime(rs.getTimestamp("departure_time"));
        b.setArrivalTime(rs.getTimestamp("arrival_time"));
        b.setPrice(rs.getBigDecimal("price"));
        return b;
    }
}
