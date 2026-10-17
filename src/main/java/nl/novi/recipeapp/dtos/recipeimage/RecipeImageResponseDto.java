package nl.novi.recipeapp.dtos.recipeimage;

import java.time.LocalDateTime;

public record RecipeImageResponseDto(
        Long recipeImageId,
        String imageUrl,
        Long recipeId,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
}
