<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List,com.mahipal.airline.model.Flight" %>
<%
    List<Flight> flights = (List<Flight>) request.getAttribute("flights");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin | Flights</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<header class="nav">
    <a class="brand" href="../index.jsp">✈ SkyReserve Admin</a>
    <nav><a href="../index.jsp">Home</a><a href="../logout">Logout</a></nav>
</header>

<main class="container page">
    <% if (request.getParameter("message") != null) { %>
        <div class="alert success"><%= request.getParameter("message") %></div>
    <% } %>

    <h1>Flight Management</h1>

    <form class="form-card admin-form" action="flights" method="post">
        <div class="form-grid">
            <div><label>Flight Number</label><input name="flightNumber" placeholder="AI701" required></div>
            <div><label>Airline</label><input name="airline" placeholder="Air India" required></div>
            <div><label>Origin</label><input name="origin" placeholder="Hyderabad" required></div>
            <div><label>Destination</label><input name="destination" placeholder="Delhi" required></div>
            <div><label>Departure</label><input type="datetime-local" name="departureTime" required></div>
            <div><label>Arrival</label><input type="datetime-local" name="arrivalTime" required></div>
            <div><label>Price</label><input type="number" step="0.01" min="0" name="price" required></div>
            <div><label>Total Seats</label><input type="number" min="1" max="500" name="totalSeats" value="60" required></div>
        </div>
        <button class="btn" type="submit">Add Flight</button>
    </form>

    <h2>Existing flights</h2>
    <div class="table-wrap">
    <table>
        <thead><tr><th>Flight</th><th>Route</th><th>Departure</th><th>Price</th><th>Available</th></tr></thead>
        <tbody>
        <% if (flights != null) for (Flight f : flights) { %>
            <tr>
                <td><b><%= f.getFlightNumber() %></b><br><small><%= f.getAirline() %></small></td>
                <td><%= f.getOrigin() %> → <%= f.getDestination() %></td>
                <td><%= f.getDepartureTime() %></td>
                <td>₹<%= f.getPrice() %></td>
                <td><%= f.getAvailableSeats() %>/<%= f.getTotalSeats() %></td>
            </tr>
        <% } %>
        </tbody>
    </table>
    </div>
</main>
</body>
</html>
