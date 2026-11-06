package nl.novi.recipeapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import nl.novi.recipeapp.dtos.reviewrating.ReviewRatingRequestDto;
import nl.novi.recipeapp.dtos.reviewrating.ReviewRatingResponseDto;
import nl.novi.recipeapp.services.ReviewRatingService;
import nl.novi.recipeapp.utils.LocationUriBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/review_ratings")
@Tag(
        name = "Beoordelingen",
        description = "API voor het beheren van receptbeoordelingen"
)
public class ReviewRatingController {

    private final ReviewRatingService reviewRatingService;
    private final LocationUriBuilder locationUriBuilder;

    public ReviewRatingController(
            ReviewRatingService reviewRatingService, LocationUriBuilder locationUriBuilder) {
        this.reviewRatingService = reviewRatingService;
        this.locationUriBuilder = locationUriBuilder;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'CHEF', 'ADMIN')")
    @Operation(
            summary = "Alle beoordelingen ophalen",
            description = "Haalt alle receptbeoordelingen op."
    )
    public ResponseEntity<List<ReviewRatingResponseDto>>
    getAllReviewRatings() {
        return ResponseEntity.ok(
                reviewRatingService.getAllReviewRatings()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'CHEF', 'ADMIN')")
    @Operation(
            summary = "Beoordeling ophalen",
            description = "Haalt een specifieke beoordeling op aan de hand van het ID."
    )
    public ResponseEntity<ReviewRatingResponseDto>
    getReviewRatingById(@PathVariable Long id) {
        return ResponseEntity.ok(
                reviewRatingService.getReviewRatingById(id)
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'CHEF', 'ADMIN')")
    @Operation(
            summary = "Beoordeling aanmaken",
            description = "Maakt een nieuwe beoordeling voor een recept aan. Alleen ingelogde gebruikers hebben toegang."
    )
    public ResponseEntity<ReviewRatingResponseDto>
    createReviewRating(
            @PathVariable Long id,
            @Valid @RequestBody ReviewRatingRequestDto requestDto) {

        ReviewRatingResponseDto createdReviewRating =
                reviewRatingService.createReviewRating(id, requestDto);

        URI location = locationUriBuilder.buildLocation(
                createdReviewRating.recipeId()
        );

        return ResponseEntity
                .created(location)
                .body(createdReviewRating);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'CHEF', 'ADMIN')")
    @Operation(
            summary = "Beoordeling wijzigen",
            description = "Wijzigt een bestaande beoordeling op basis van het ID. Alleen ingelogde gebruikers hebben toegang."
    )
    public ResponseEntity<ReviewRatingResponseDto>
    updateReviewRating(
            @PathVariable Long id,
            @Valid @RequestBody ReviewRatingRequestDto requestDto) {

        return ResponseEntity.ok(
                reviewRatingService.updateReviewRating(id, requestDto)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'CHEF', 'ADMIN')")
    @Operation(
            summary = "Beoordeling verwijderen",
            description = "Verwijdert een beoordeling op basis van het ID. Alleen ingelogde gebruikers hebben toegang."
    )
    public ResponseEntity<Void> deleteReviewRating(
            @PathVariable Long id) {

        reviewRatingService.deleteReviewRating(id);

        return ResponseEntity.noContent().build();
    }
}
