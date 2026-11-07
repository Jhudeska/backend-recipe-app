
package nl.novi.recipeapp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import nl.novi.recipeapp.services.UserProfileService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class UserProfileSyncFilter extends OncePerRequestFilter {

    private final UserProfileService userProfileService;

    public UserProfileSyncFilter(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println("UserProfileSyncFilter gestart!");

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuth
                && authentication.isAuthenticated()) {

            String keycloakId = jwtAuth.getToken().getSubject();

            System.out.println("JWT gevonden!");
            System.out.println("Keycloak ID: " + keycloakId);

            try {
                userProfileService.getOrCreateUserProfile(
                        jwtAuth.getToken()
                );

                System.out.println("UserProfile succesvol gecontroleerd!");

            } catch (Exception e) {
                System.err.println(
                        "Fout bij synchroniseren gebruiker: " + e.getMessage()
                );
                throw e;
            }

        } else {
            System.out.println("Geen JwtAuthenticationToken gevonden!");
        }

        filterChain.doFilter(request, response);
    }
@Override
protected boolean shouldNotFilter(HttpServletRequest request) {

    String path = request.getServletPath();

    return path.startsWith("/swagger-ui")
            || path.startsWith("/v3/api-docs");
}

}
