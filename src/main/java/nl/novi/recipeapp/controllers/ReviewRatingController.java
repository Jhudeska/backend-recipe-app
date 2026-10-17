package nl.novi.recipeapp.controllers;

import jakarta.validation.Valid;
import nl.novi.recipeapp.dtos.reviewrating.ReviewRatingRequestDto;
import nl.novi.recipeapp.dtos.reviewrating.ReviewRatingResponseDto;
import nl.novi.recipeapp.services.ReviewRatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review_ratings")
public class ReviewRatingController {

    private final ReviewRatingService reviewRatingService;

    public ReviewRatingController(
            ReviewRatingService reviewRatingService) {
        this.reviewRatingService = reviewRatingService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewRatingResponseDto>>
    getAllReviewRatings() {
        return ResponseEntity.ok(
                reviewRatingService.getAllReviewRatings()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewRatingResponseDto>
    getReviewRatingById(@PathVariable Long id) {
        return ResponseEntity.ok(
                reviewRatingService.getReviewRatingById(id)
        );
    }

    @PostMapping
    public ResponseEntity<ReviewRatingResponseDto>
    createReviewRating(
            @PathVariable Long id,
            @Valid @RequestBody ReviewRatingRequestDto requestDto) {

        ReviewRatingResponseDto createdReviewRating =
                reviewRatingService.createReviewRating(id, requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdReviewRating);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewRatingResponseDto>
    updateReviewRating(
            @PathVariable Long id,
            @Valid @RequestBody ReviewRatingRequestDto requestDto) {

        return ResponseEntity.ok(
                reviewRatingService.updateReviewRating(id, requestDto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReviewRating(
            @PathVariable Long id) {

        reviewRatingService.deleteReviewRating(id);

        return ResponseEntity.noContent().build();
    }
}
