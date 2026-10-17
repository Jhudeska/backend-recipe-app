package nl.novi.recipeapp.mappers;

import nl.novi.recipeapp.dtos.reviewrating.ReviewRatingResponseDto;
import nl.novi.recipeapp.entities.ReviewRatingEntity;
import org.springframework.stereotype.Component;

@Component
public class ReviewRatingMapper {

    public ReviewRatingResponseDto toResponseDto(
            ReviewRatingEntity reviewRating) {

        return new ReviewRatingResponseDto(
                reviewRating.getReviewRatingId(),
                reviewRating.getComment(),
                reviewRating.getRatingScore(),
                reviewRating.getRecipe().getRecipeId(),
                reviewRating.getRecipe().getTitle(),
                reviewRating.getUserProfile().getUserId(),
                reviewRating.getCreated_at(),
                reviewRating.getUpdated_at()
        );
    }
}
