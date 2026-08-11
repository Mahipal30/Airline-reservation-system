<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List,com.mahipal.airline.model.Flight" %>
<%
    List<Flight> flights = (List<Flight>) request.getAttribute("flights");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Flight Results | SkyReserve</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<header class="nav">
    <a class="brand" href="index.jsp">✈ SkyReserve</a>
    <nav><a href="index.jsp">New Search</a></nav>
</header>

<main class="container page">
    <h1>Available flights</h1>
    <p class="muted"><%= request.getAttribute("origin") %> → <%= request.getAttribute("destination") %> · <%= request.getAttribute("date") %></p>

    <% if (flights == null || flights.isEmpty()) { %>
        <div class="empty">
            <h2>No flights found</h2>
            <p>Try another route or date.</p>
            <a class="btn" href="index.jsp">Search again</a>
        </div>
    <% } else { %>
        <div class="flight-list">
        <% for (Flight f : flights) { %>
            <div class="flight-card">
                <div>
                    <span class="badge"><%= f.getFlightNumber() %></span>
                    <h2><%= f.getAirline() %></h2>
                    <p><%= f.getOrigin() %> → <%= f.getDestination() %></p>
                </div>
                <div class="time">
                    <b><%= f.getDepartureTime() %></b>
                    <span>to</span>
                    <b><%= f.getArrivalTime() %></b>
                </div>
                <div>
                    <span class="muted"><%= f.getAvailableSeats() %> seats left</span>
                    <h2>₹<%= f.getPrice() %></h2>
                    <a class="btn small" href="book?flightId=<%= f.getId() %>">Book now</a>
                </div>
            </div>
        <% } %>
        </div>
    <% } %>
</main>
</body>
</html>
