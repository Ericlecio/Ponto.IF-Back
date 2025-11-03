package br.edu.ifpe.pontoif.pontoif.configurations;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        errorDetails.put("error", "Unauthorized");

        String message = getString(authException);

        errorDetails.put("message", message);
        response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
    }

    private static String getString(AuthenticationException authException) {
        String message = "Authentication required or authentication failed.";

        Throwable cause = authException.getCause();
        if (cause instanceof TokenExpiredException) {
            message = "JWT token expired. Please log in again.";
        } else if (cause instanceof JWTVerificationException) {
            message = "Invalid JWT Token:" + cause.getMessage();
        } else if (authException.getMessage() != null && !authException.getMessage().isEmpty()) {
            message = authException.getMessage();
        }
        return message;
    }

}
