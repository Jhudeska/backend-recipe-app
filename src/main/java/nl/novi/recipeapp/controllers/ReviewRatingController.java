package nl.novi.recipeapp.controllers;

import nl.novi.recipeapp.entities.ReviewRatingEntity;
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
    public ResponseEntity<List<ReviewRatingEntity>>
    getAllReviewRatings() {
        return ResponseEntity.ok(
                reviewRatingService.getAllReviewRatings()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewRatingEntity>
    getReviewRatingById(@PathVariable Long id) {
        return ResponseEntity.ok(
                reviewRatingService.getReviewRatingById(id)
        );
    }

    @PostMapping
    public ResponseEntity<ReviewRatingEntity>
    createReviewRating(
            @RequestBody ReviewRatingEntity reviewRating) {

        ReviewRatingEntity createdReviewRating =
                reviewRatingService.createReviewRating(reviewRating);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdReviewRating);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewRatingEntity>
    updateReviewRating(
            @PathVariable Long id,
            @RequestBody ReviewRatingEntity reviewRating) {

        return ResponseEntity.ok(
                reviewRatingService.updateReviewRating(id, reviewRating)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReviewRating(
            @PathVariable Long id) {

        reviewRatingService.deleteReviewRating(id);

        return ResponseEntity.noContent().build();
    }
}
