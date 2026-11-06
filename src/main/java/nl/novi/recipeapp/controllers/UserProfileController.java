package nl.novi.recipeapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import nl.novi.recipeapp.dtos.userprofile.UserProfileRequestDto;
import nl.novi.recipeapp.dtos.userprofile.UserProfileResponseDto;
import nl.novi.recipeapp.dtos.userprofile.UserProfileUpdateDto;
import nl.novi.recipeapp.entities.UserProfileEntity;
import nl.novi.recipeapp.services.UserProfileService;
import nl.novi.recipeapp.utils.LocationUriBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user_profiles")
@Tag(name = "Gebruikersprofielen", description = "API voor het beheren van gebruikersprofielen")
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final LocationUriBuilder locationUriBuilder;

    public UserProfileController(
            UserProfileService userProfileService, LocationUriBuilder locationUriBuilder) {
        this.userProfileService = userProfileService;
        this.locationUriBuilder = locationUriBuilder;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Alle gebruikersprofielen ophalen",
            description = "Haalt alle gebruikersprofielen op. Alleen ADMIN heeft toegang."
    )
    public ResponseEntity<List<UserProfileResponseDto>> getAllUserProfiles() {
        return ResponseEntity.ok(
                userProfileService.getAllUserProfiles()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Gebruikersprofiel ophalen",
            description = "Haalt een specifiek gebruikersprofiel op aan de hand van het ID. Alleen ADMIN heeft toegang."
    )
    public ResponseEntity<UserProfileResponseDto> getUserProfileById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                userProfileService.getUserProfileById(id)
        );
    }

    // Eigen profiel ophalen
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER', 'CHEF', 'ADMIN')")
    @Operation(
            summary = "Gebruikersprofiel aanmaken",
            description = "Maakt een nieuw gebruikersprofiel aan. Alleen ADMIN heeft toegang."
    )
    public ResponseEntity<UserProfileResponseDto> getMe(
            @AuthenticationPrincipal Jwt jwt) {

        return ResponseEntity.ok(
                userProfileService.getCurrentUserProfile(jwt)
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Gebruikersprofiel aanmaken",
            description = "Maakt een nieuw gebruikersprofiel aan. Alleen ADMIN heeft toegang."
    )
    public ResponseEntity<UserProfileResponseDto> createUserProfile(
            @Valid @RequestBody UserProfileRequestDto requestDto) {

        UserProfileResponseDto createdUserProfile =
                userProfileService.createUserProfile(requestDto);

        URI location = locationUriBuilder.buildLocation(
                createdUserProfile.userId()
        );

        return ResponseEntity
                .created(location)
                .body(createdUserProfile);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(
            summary = "Gebruikersprofiel wijzigen",
            description = "Wijzigt een bestaand gebruikersprofiel op basis van het ID. Alleen ADMIN heeft toegang."
    )
    public ResponseEntity<UserProfileResponseDto> updateUserProfile(
            @PathVariable Long id,
            @Valid @RequestBody UserProfileUpdateDto requestDto) {

        return ResponseEntity.ok(
                userProfileService.updateUserProfile(id, requestDto)
        );
    }

    // Eigen profiel aanpassen
    @PutMapping("/me")
    @PreAuthorize("hasAnyRole('USER', 'CHEF', 'ADMIN')")
    @Operation(
            summary = "Eigen profiel wijzigen",
            description = "Wijzigt de gegevens van het profiel van de momenteel ingelogde gebruiker."
    )
    public ResponseEntity<UserProfileResponseDto> updateMe(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody UserProfileUpdateDto requestDto) {

        return ResponseEntity.ok(
                userProfileService.updateCurrentUserProfile(jwt, requestDto)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Gebruikersprofiel verwijderen",
            description = "Verwijdert een gebruikersprofiel op basis van het ID. Alleen ADMIN heeft toegang."
    )
    public ResponseEntity<Void> deleteUserProfile(
            @PathVariable Long id) {
        userProfileService.deleteUserProfile(id);

        return ResponseEntity.noContent().build();
    }

}

