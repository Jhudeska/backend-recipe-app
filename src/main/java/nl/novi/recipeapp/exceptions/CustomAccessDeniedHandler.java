
package nl.novi.recipeapp.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exception) throws IOException {

        System.err.println("========== ACCESS DENIED ==========");
        System.err.println("REQUEST URI: " + request.getRequestURI());
        System.err.println("HTTP METHOD: " + request.getMethod());
        System.err.println("ERROR: " + exception.getMessage());
        System.err.println("===================================");

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write("""
                {
                    "status": 403,
                    "error": "Forbidden",
                    "message": "Je hebt geen toestemming om deze actie uit te voeren."
                }
                """);
    }
}
