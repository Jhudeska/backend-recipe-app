package nl.novi.recipeapp.security;

import nl.novi.recipeapp.exceptions.CustomAccessDeniedHandler;
import nl.novi.recipeapp.exceptions.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import nl.novi.recipeapp.services.UserProfileService;
import org.springframework.security.web.access.intercept.AuthorizationFilter;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;
    @Value("${spring.security.oauth2.resourceserver.jwt.audience}")
    private String audience;
    @Value("${client-id}")
    private String clientId;

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            CustomAuthenticationEntryPoint authenticationEntryPoint,
            CustomAccessDeniedHandler customAccessDeniedHandler,
            UserProfileService userProfileService
            ) throws Exception {

        System.out.println("SECURITY CONFIG WORDT GELADEN!");
        return http
            .httpBasic(hp -> hp.disable())
            .csrf(csrf->csrf.disable())
            .cors(cors->{})
            .oauth2ResourceServer(oauth2 -> oauth2
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .jwt(jwt -> jwt
                            .jwtAuthenticationConverter(jwtAuthenticationConverter())
                            .decoder(jwtDecoder())
                    ))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                // =====================================
                // REQUEST MATCHERS
                // =====================================

                .authorizeHttpRequests(authorize -> authorize


                                // =====================================
                                // 1. SWAGGER
                                // =====================================
                                .requestMatchers(
                                        "/swagger-ui.html",
                                        "/swagger-ui/**",
                                        "/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/webjars/**"
                                ).permitAll()

                                // =====================================
                                // 2. PUBLIEK: GUEST + USER + CHEF + ADMIN
                                // =====================================

                                // Recepten bekijken, zoeken en formulierdata
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/v1/recipes",
                                        "/api/v1/recipes/**"
                                ).permitAll()

                                // Ingrediënten bekijken
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/v1/ingredients",
                                        "/api/v1/ingredients/**"
                                ).permitAll()

                                // Categorieën bekijken
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/v1/categories",
                                        "/api/v1/categories/**"
                                ).permitAll()

                                // =====================================
                                // 3. RECEPTEN: CHEF + ADMIN
                                // =====================================

                                // POST
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/v1/recipes",
                                        "/api/v1/ingredients",
                                        "/api/v1/categories",
                                        "/api/v1/recipe_images",
                                        "/api/v1/recipe_ingredients"
                                ).hasAnyRole("CHEF", "ADMIN")

                                // PUT
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/v1/recipes/**",
                                        "/api/v1/ingredients/**",
                                        "/api/v1/categories/**",
                                        "/api/v1/recipe_images/**"
                                ).hasAnyRole("CHEF", "ADMIN")

                                // DELETE
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/v1/recipes/**",
                                        "/api/v1/ingredients/**",
                                        "/api/v1/categories/**",
                                        "/api/v1/recipe_images/**",
                                        "/api/v1/recipe_ingredients/**"
                                ).hasAnyRole("CHEF", "ADMIN")


                                // =====================================
                                // 4. USER PROFILE
                                // =====================================

                                // Eigen profiel ophalen
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/v1/user_profiles/me"
                                ).hasAnyRole("USER", "CHEF", "ADMIN")

                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/v1/user_profiles/me"
                                ).hasAnyRole("USER", "CHEF", "ADMIN")

                                // Profiel aanpassen: ingelogd, eigenaarscontrole in Service
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/v1/user_profiles/**"
                                ).hasAnyRole("USER", "CHEF", "ADMIN")


                                // ================================================
                                // 5. ALLE INGLOGDE GEBRUIKERS FAVORITES EN REVIEWS
                                // ================================================
                                .requestMatchers(
                                        "/api/v1/user_profiles/**",
                                        "/api/v1/review_ratings/**",
                                        "/api/v1/favorites/**",
                                        "/api/v1/recipe_images/**",
                                        "/api/v1/recipe_ingredients/**"
                                ).authenticated()


                                // =====================================
                                // 6. OVERIGE ALLEEN: ADMIN
                                // =====================================

                                // Alleen admin mag alle gebruikers beheren
                                .requestMatchers(
                                        "/api/v1/user_profile_roles/**"
                                ).hasRole("ADMIN")

                                // Alleen ADMIN mag gebruikers verwijderen
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/v1/user_profiles/**"
                                ).hasRole("ADMIN")

                                // =====================================
                                // 7. OVERIGE ENDPOINTS WEIGEREN
                                // =====================================

                                // ADMIN krijgt toegang tot overige API-endpoints
                                .requestMatchers(
                                        "/api/v1/**"
                                ).hasRole("ADMIN")

                                // Overige requests weigeren
                                .anyRequest().denyAll()
//                                .anyRequest().permitAll()
//                                .anyRequest().authenticated()

            )
            .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
             .addFilterAfter(
                     new UserProfileSyncFilter(userProfileService),
                     BearerTokenAuthenticationFilter.class
            )
                .build();

    }

    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new Converter<>() {
            @Override
            public Collection<GrantedAuthority> convert(Jwt source) {
                Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                for (String authority : getAuthorities(source)) {
                    grantedAuthorities.add(
                            new SimpleGrantedAuthority(

                                            authority.startsWith("ROLE_")
                                                    ? authority
                                                    : "ROLE_" + authority
                            )
                    );
                }
                return grantedAuthorities;
            }
            private List<String> getAuthorities(Jwt jwt){
                Map<String, Object> resourceAcces = jwt.getClaim("resource_access");
                if(resourceAcces != null){
                    if( resourceAcces.get(clientId) instanceof Map) {
                        Map<String, Object> client = (Map<String, Object>) resourceAcces.get(clientId);
                        if(client != null && client.containsKey("roles")) {
                            return (List<String>) client.get("roles");
                        }
                    }
                }
                return new ArrayList<>();
            }
        });
        return jwtAuthenticationConverter;
    }

    public JwtDecoder jwtDecoder(){
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer);

        OAuth2TokenValidator<Jwt> audienceValidator = new JwtAudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);
        jwtDecoder.setJwtValidator(withAudience);

        return jwtDecoder;
    }

    public class JwtAudienceValidator implements OAuth2TokenValidator<Jwt> {
        private final String audience;
        public JwtAudienceValidator(String audience) {
            this.audience = audience;
        }

        @Override
        public OAuth2TokenValidatorResult validate(final Jwt jwt) {
            OAuth2Error error = new OAuth2Error("invalid_token", "The required audience is missing!", null);
            if (jwt.getAudience().contains(audience)) {
                return OAuth2TokenValidatorResult.success();
            }
            return OAuth2TokenValidatorResult.failure(error);
        }
    }
}
