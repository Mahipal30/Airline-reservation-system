package com.mahipal.airline.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Booking {
    private int id;
    private String bookingReference;
    private int userId;
    private int flightId;
    private String passengerName;
    private String passengerEmail;
    private String passengerPhone;
    private String seatNumber;
    private String status;
    private String paymentStatus;
    private Timestamp bookedAt;

    private String flightNumber;
    private String airline;
    private String origin;
    private String destination;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private BigDecimal price;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getBookingReference() { return bookingReference; }
    public void setBookingReference(String v) { bookingReference = v; }
    public int getUserId() { return userId; }
    public void setUserId(int v) { userId = v; }
    public int getFlightId() { return flightId; }
    public void setFlightId(int v) { flightId = v; }
    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String v) { passengerName = v; }
    public String getPassengerEmail() { return passengerEmail; }
    public void setPassengerEmail(String v) { passengerEmail = v; }
    public String getPassengerPhone() { return passengerPhone; }
    public void setPassengerPhone(String v) { passengerPhone = v; }
    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String v) { seatNumber = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { status = v; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String v) { paymentStatus = v; }
    public Timestamp getBookedAt() { return bookedAt; }
    public void setBookedAt(Timestamp v) { bookedAt = v; }
    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String v) { flightNumber = v; }
    public String getAirline() { return airline; }
    public void setAirline(String v) { airline = v; }
    public String getOrigin() { return origin; }
    public void setOrigin(String v) { origin = v; }
    public String getDestination() { return destination; }
    public void setDestination(String v) { destination = v; }
    public Timestamp getDepartureTime() { return departureTime; }
    public void setDepartureTime(Timestamp v) { departureTime = v; }
    public Timestamp getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(Timestamp v) { arrivalTime = v; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal v) { price = v; }
}
