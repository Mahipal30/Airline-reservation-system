package com.mahipal.airline.filter;

import com.mahipal.airline.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter(urlPatterns = {"/book", "/my-bookings", "/cancel-booking", "/admin/*"})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            res.sendRedirect(req.getContextPath() + "/login.jsp?message=Please+login+first");
            return;
        }

        String uri = req.getRequestURI();
        if (uri.contains("/admin/") && !"ADMIN".equals(user.getRole())) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Admin access required.");
            return;
        }

        chain.doFilter(request, response);
    }
}
