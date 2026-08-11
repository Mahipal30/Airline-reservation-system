<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register | SkyReserve</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="auth-page">
    <div class="auth-card">
        <a class="brand" href="index.jsp">✈ SkyReserve</a>
        <h1>Create account</h1>
        <p>Register to book and manage flights.</p>

        <% if (request.getParameter("error") != null) { %>
            <div class="alert error"><%= request.getParameter("error") %></div>
        <% } %>

        <form action="register" method="post">
            <label>Full Name</label>
            <input name="fullName" required maxlength="100">

            <label>Email</label>
            <input type="email" name="email" required maxlength="150">

            <label>Password</label>
            <input type="password" name="password" minlength="6" required>

            <button class="btn full" type="submit">Register</button>
        </form>

        <p class="muted">Already registered? <a href="login.jsp">Login</a></p>
    </div>
</div>
</body>
</html>
