<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login | SkyReserve</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="auth-page">
    <div class="auth-card">
        <a class="brand" href="index.jsp">✈ SkyReserve</a>
        <h1>Welcome back</h1>
        <p>Sign in to manage your reservations.</p>

        <% if (request.getParameter("error") != null) { %>
            <div class="alert error"><%= request.getParameter("error") %></div>
        <% } %>
        <% if (request.getParameter("message") != null) { %>
            <div class="alert success"><%= request.getParameter("message") %></div>
        <% } %>

        <form action="login" method="post">
            <label>Email</label>
            <input type="email" name="email" required>

            <label>Password</label>
            <input type="password" name="password" required>

            <button class="btn full" type="submit">Login</button>
        </form>

        <p class="muted">New traveler? <a href="register.jsp">Create an account</a></p>
        <p class="demo">Demo admin: admin@airline.com / Admin@123</p>
    </div>
</div>
</body>
</html>
