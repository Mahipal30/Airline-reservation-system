<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List,com.mahipal.airline.model.Booking" %>
<%
    List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");
    Booking justBooked = (Booking) request.getAttribute("booking");
%>
<!DOCTYPE html>
<html>
<head>
    <title>My Bookings | SkyReserve</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<header class="nav">
    <a class="brand" href="index.jsp">✈ SkyReserve</a>
    <nav><a href="index.jsp">Home</a><a href="logout">Logout</a></nav>
</header>

<main class="container page">
    <% if (justBooked != null) { %>
        <div class="alert success">
            Booking confirmed! Your reference is <strong><%= justBooked.getBookingReference() %></strong>.
            Seat <strong><%= justBooked.getSeatNumber() %></strong> is reserved.
        </div>
    <% } %>
    <% if (request.getParameter("message") != null) { %>
        <div class="alert success"><%= request.getParameter("message") %></div>
    <% } %>

    <h1>My bookings</h1>

    <% if (bookings == null || bookings.isEmpty()) { %>
        <div class="empty">
            <h2>No bookings yet</h2>
            <a class="btn" href="index.jsp">Find a flight</a>
        </div>
    <% } else { %>
        <div class="booking-list">
        <% for (Booking b : bookings) { %>
            <div class="booking-card">
                <div class="booking-top">
                    <span class="badge"><%= b.getBookingReference() %></span>
                    <span class="<%= "CONFIRMED".equals(b.getStatus()) ? "status confirmed" : "status cancelled" %>">
                        <%= b.getStatus() %>
                    </span>
                </div>
                <h2><%= b.getAirline() %> · <%= b.getFlightNumber() %></h2>
                <p><%= b.getOrigin() %> → <%= b.getDestination() %></p>
                <div class="booking-grid">
                    <span><small>Passenger</small><b><%= b.getPassengerName() %></b></span>
                    <span><small>Seat</small><b><%= b.getSeatNumber() %></b></span>
                    <span><small>Departure</small><b><%= b.getDepartureTime() %></b></span>
                    <span><small>Amount</small><b>₹<%= b.getPrice() %></b></span>
                </div>
                <% if ("CONFIRMED".equals(b.getStatus())) { %>
                    <form action="cancel-booking" method="post" onsubmit="return confirmCancel()">
                        <input type="hidden" name="bookingId" value="<%= b.getId() %>">
                        <button class="btn danger" type="submit">Cancel Booking</button>
                    </form>
                <% } %>
            </div>
        <% } %>
        </div>
    <% } %>
</main>
<script src="js/app.js"></script>
</body>
</html>
