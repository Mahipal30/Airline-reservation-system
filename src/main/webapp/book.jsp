<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.mahipal.airline.model.Flight" %>
<%
    Flight flight = (Flight) request.getAttribute("flight");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Book Flight | SkyReserve</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<header class="nav">
    <a class="brand" href="index.jsp">✈ SkyReserve</a>
    <nav><a href="my-bookings">My Bookings</a></nav>
</header>

<main class="container page narrow">
    <h1>Complete your booking</h1>

    <% if (request.getAttribute("error") != null) { %>
        <div class="alert error"><%= request.getAttribute("error") %></div>
    <% } %>

    <% if (flight != null) { %>
    <div class="summary">
        <b><%= flight.getFlightNumber() %> · <%= flight.getAirline() %></b>
        <span><%= flight.getOrigin() %> → <%= flight.getDestination() %></span>
        <span>₹<%= flight.getPrice() %> · <%= flight.getAvailableSeats() %> seats available</span>
    </div>

    <form class="form-card" action="book" method="post">
        <input type="hidden" name="flightId" value="<%= flight.getId() %>">

        <label>Passenger Name</label>
        <input name="passengerName" required maxlength="100">

        <label>Passenger Email</label>
        <input type="email" name="passengerEmail" required maxlength="150">

        <label>Passenger Phone</label>
        <input name="passengerPhone" required maxlength="25">

        <label>Seat Number</label>
        <select name="seatNumber" required>
            <option value="">Select a seat</option>
            <% for (int row = 1; row <= 10; row++) {
                   for (char col = 'A'; col <= 'F'; col++) { %>
                <option value="<%= row %><%= col %>"><%= row %><%= col %></option>
            <% } } %>
        </select>

        <button class="btn full" type="submit">Confirm Reservation</button>
        <p class="muted">Payment is represented as PENDING in this portfolio project; no real payment is charged.</p>
    </form>
    <% } %>
</main>
</body>
</html>
