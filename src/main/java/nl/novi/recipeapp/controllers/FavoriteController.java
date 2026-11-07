package nl.novi.recipeapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import nl.novi.recipeapp.dtos.favorite.FavoriteRequestDto;
import nl.novi.recipeapp.dtos.favorite.FavoriteResponseDto;
import nl.novi.recipeapp.entities.UserProfileEntity;
import nl.novi.recipeapp.services.FavoriteService;
import nl.novi.recipeapp.utils.LocationUriBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import nl.novi.recipeapp.enums.Role;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/favorites")
@Tag(name = "Favorieten", description = "API voor het beheren van favoriete recepten")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final LocationUriBuilder locationUriBuilder;

    public FavoriteController(FavoriteService favoriteService, LocationUriBuilder locationUriBuilder) {
        this.favoriteService = favoriteService;
        this.locationUriBuilder = locationUriBuilder;
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'CHEF', 'ADMIN')")
    @Operation(
            summary = "Favorieten van gebruiker ophalen",
            description = "Haalt alle favoriete recepten op van een specifieke gebruiker."
    )
    public ResponseEntity<List<FavoriteResponseDto>> getAllFavorites(
            @PathVariable Long userId,
            @AuthenticationPrincipal Jwt jwt
            ) {

//        checkUserAccess(userId, jwt);

        return ResponseEntity
                .ok(favoriteService.getAllFavorites(userId));
    }

    @GetMapping("/user/{userId}/recipe/{recipeId}")
    @PreAuthorize("hasAnyRole('USER', 'CHEF', 'ADMIN')")
    @Operation(
            summary = "Favoriet ophalen",
            description = "Haalt een favoriet op van een specifieke gebruiker voor een bepaald recept."
    )
    public ResponseEntity<FavoriteResponseDto> getFavoriteByRecipeId(
            @PathVariable Long userId,
            @PathVariable Long recipeId,
            @AuthenticationPrincipal Jwt jwt) {

//        checkUserAccess(userId, jwt);

        return ResponseEntity
                .ok(favoriteService.getFavoriteByRecipeId(userId, recipeId));
    }

    @PostMapping("/user/{userId}/recipe/{recipeId}")
    @PreAuthorize("hasAnyRole('USER', 'CHEF', 'ADMIN')")
    @Operation(
            summary = "Recept toevoegen aan favorieten",
            description = "Voegt een recept toe aan de favorieten van een specifieke gebruiker."
    )
    public ResponseEntity<FavoriteResponseDto> createFavorite(
            @PathVariable Long userId,
            @PathVariable Long recipeId,
//            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody FavoriteRequestDto requestDto) {

//        checkUserAccess(userId, jwt);

        FavoriteResponseDto newFavorite =
                favoriteService.createFavorite(userId, requestDto);

        URI location = locationUriBuilder.buildLocation(
                newFavorite.favoriteId()
        );

        return ResponseEntity
                .created(location)
                .body(newFavorite);
    }

    @DeleteMapping("/user/{userId}/recipe/{recipeId}")
    @PreAuthorize("hasAnyRole('USER', 'CHEF', 'ADMIN')")
    @Operation(
            summary = "Favoriet verwijderen",
            description = "Verwijdert een recept uit de favorieten van een specifieke gebruiker."
    )
    public ResponseEntity<Void> deleteFavorite(
            @PathVariable Long userId,
            @PathVariable Long recipeId,
            @AuthenticationPrincipal Jwt jwt
    ) {


//        checkUserAccess(userId, jwt);

        favoriteService.deleteFavorite(userId, recipeId);

        return ResponseEntity.noContent().build();
    }


    private void checkUserAccess(Long userId, Jwt jwt) {

        UserProfileEntity currentUser =
                favoriteService.getUserProfileByKeycloakId(
                        jwt.getSubject()
                );

        boolean isAdmin =
                currentUser.getRoles().contains(Role.ADMIN);

        if (!isAdmin &&
                !currentUser.getUserId().equals(userId)) {

            throw new AccessDeniedException(
                    "You are not allowed to access these favorites."
            );
        }
    }
}