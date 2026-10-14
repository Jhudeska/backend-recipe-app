package nl.novi.recipeapp.services;

import jakarta.transaction.Transactional;
import nl.novi.recipeapp.entities.RecipeEntity;
import nl.novi.recipeapp.entities.ReviewRatingEntity;
import nl.novi.recipeapp.entities.UserProfileEntity;
import nl.novi.recipeapp.repositories.RecipeRepository;
import nl.novi.recipeapp.repositories.ReviewRatingRepository;
import nl.novi.recipeapp.repositories.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewRatingService {

    private final ReviewRatingRepository reviewRatingRepository;
    private final UserProfileRepository userProfileRepository;
    private final RecipeRepository recipeRepository;

    public ReviewRatingService(
            ReviewRatingRepository reviewRatingRepository,
            UserProfileRepository userProfileRepository,
            RecipeRepository recipeRepository) {
        this.reviewRatingRepository = reviewRatingRepository;
        this.userProfileRepository = userProfileRepository;
        this.recipeRepository = recipeRepository;
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

    @Transactional
    public ReviewRatingEntity createReviewRating(
            ReviewRatingEntity reviewRating) {

        Long userId = reviewRating.getUserProfile().getUserId();
        Long recipeId = reviewRating.getRecipe().getRecipeId();

        UserProfileEntity userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("Gebruiker niet gevonden"));

        RecipeEntity recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() ->
                        new RuntimeException("Recept niet gevonden"));

        reviewRating.setUserProfile(userProfile);
        reviewRating.setRecipe(recipe);

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
        existingReviewRating.setUserProfile(reviewRating.getUserProfile());
        existingReviewRating.setRecipe(reviewRating.getRecipe());

        Long userId = reviewRating.getUserProfile().getUserId();
        Long recipeId = reviewRating.getRecipe().getRecipeId();

        UserProfileEntity userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("Gebruiker niet gevonden"));

        RecipeEntity recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() ->
                        new RuntimeException("Recept niet gevonden"));

        existingReviewRating.setUserProfile(userProfile);
        existingReviewRating.setRecipe(recipe);

        return reviewRatingRepository.save(existingReviewRating);
    }

    public void deleteReviewRating(Long id) {
        ReviewRatingEntity existingReviewRating =
                getReviewRatingById(id);

        reviewRatingRepository.delete(existingReviewRating);
    }
}
