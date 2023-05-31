package app.security.config;

import app.model.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
public class JwtFilter extends GenericFilterBean {
    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String authHeader = request.getHeader("authorization");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");

        if ("OPTIONS".equals(request.getMethod()) || request.getServletPath().contains("/api/auth/*")) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
            return;
        }
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("An exception occurred with authentication");
        }
        final String token = authHeader.substring(7);
        String role = this.jwtService.extractRole(token);

        Claims claims = Jwts.parser().setSigningKey(this.jwtService.getSignInKey()).parseClaimsJws(token).getBody();
        request.setAttribute("claims", claims);
        request.setAttribute("blog", servletRequest.getParameter("id"));

        if (role.equals(UserRole.ADMIN.toString())) {
            filterChain.doFilter(request, response);
        } else if (role.equals(UserRole.OWNER.toString()) && request.getServletPath().startsWith("/api/user/owner")) {
            filterChain.doFilter(request, response);
        } else if (role.equals(UserRole.EMPLOYEE.toString()) && request.getServletPath().startsWith("/api/user/employee")) {
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have sufficient privileges to perform this operation.");
        }
    }
}
