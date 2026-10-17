package nl.novi.recipeapp.dtos.reviewrating;

import java.time.LocalDateTime;

public record ReviewRatingResponseDto(
        Long reviewRatingId,
        String comment,
        Integer ratingScore,
        Long recipeId,
        String recipeTitle,
        Long userId,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
}
