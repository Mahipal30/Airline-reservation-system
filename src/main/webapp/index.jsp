<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.mahipal.airline.model.User" %>
<%
    User currentUser = (User) session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
    <title>SkyReserve | Airline Reservation</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<header class="nav">
    <a class="brand" href="index.jsp">✈ SkyReserve</a>
    <nav>
        <% if (currentUser == null) { %>
            <a href="login.jsp">Login</a>
            <a class="btn small" href="register.jsp">Register</a>
        <% } else { %>
            <span>Hello, <%= currentUser.getFullName() %></span>
            <a href="my-bookings">My Bookings</a>
            <% if ("ADMIN".equals(currentUser.getRole())) { %>
                <a href="admin/flights">Admin</a>
            <% } %>
            <a href="logout">Logout</a>
        <% } %>
    </nav>
</header>

<section class="hero">
    <div class="hero-content">
        <p class="eyebrow">SMART • SIMPLE • SECURE</p>
        <h1>Book your next journey with confidence.</h1>
        <p>Search flights, select your seat and manage your reservations from one place.</p>

        <form class="search-card" action="search" method="get">
            <div>
                <label>From</label>
                <input name="origin" placeholder="Hyderabad" required>
            </div>
            <div>
                <label>To</label>
                <input name="destination" placeholder="Delhi" required>
            </div>
            <div>
                <label>Departure</label>
                <input type="date" name="date" required>
            </div>
            <button class="btn" type="submit">Search Flights</button>
        </form>

        <% if (request.getParameter("error") != null) { %>
            <div class="alert error"><%= request.getParameter("error") %></div>
        <% } %>
    </div>
</section>

<section class="container">
    <h2>Why SkyReserve?</h2>
    <div class="features">
        <div class="feature"><b>🔎 Fast Search</b><p>Find flights by route and date.</p></div>
        <div class="feature"><b>💺 Seat Selection</b><p>Choose your preferred seat.</p></div>
        <div class="feature"><b>📋 Easy Management</b><p>View and cancel bookings online.</p></div>
    </div>
</section>

<script src="js/app.js"></script>
</body>
</html>
