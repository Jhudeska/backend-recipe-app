package nl.novi.recipeapp.services;

import nl.novi.recipeapp.entities.ReviewRatingEntity;
import nl.novi.recipeapp.repositories.ReviewRatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewRatingService {
    private final ReviewRatingRepository reviewRatingRepository;

    public ReviewRatingService(
            ReviewRatingRepository reviewRatingRepository) {
        this.reviewRatingRepository = reviewRatingRepository;
    }

    public List<ReviewRatingEntity> getAllReviewRatings() {
        return reviewRatingRepository.findAll();
    }

    public ReviewRatingEntity getReviewRatingById(Long id) {
        return reviewRatingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Review niet gevonden met ID: " + id
                        )
                );
    }

    public ReviewRatingEntity createReviewRating(
            ReviewRatingEntity reviewRating) {
        return reviewRatingRepository.save(reviewRating);
    }

    public ReviewRatingEntity updateReviewRating(
            Long id,
            ReviewRatingEntity reviewRating) {

        ReviewRatingEntity existingReviewRating =
                getReviewRatingById(id);

        existingReviewRating.setComment(reviewRating.getComment());
        existingReviewRating.setRatingScore(
                reviewRating.getRatingScore()
        );
        existingReviewRating.setUserId(reviewRating.getUserId());
        existingReviewRating.setRecipeId(reviewRating.getRecipeId());

        return reviewRatingRepository.save(existingReviewRating);
    }

    public void deleteReviewRating(Long id) {
        ReviewRatingEntity existingReviewRating =
                getReviewRatingById(id);

        reviewRatingRepository.delete(existingReviewRating);
    }
}
