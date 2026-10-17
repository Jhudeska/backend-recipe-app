package nl.novi.recipeapp.dtos.recipe;

import java.time.LocalDateTime;

public record RecipeResponseDto(
        Long recipeId,
        String title,
        String description,
        String instructions,
        Integer preparationTime,
        Integer servings,
        Long categoryId,
        String categoryName,
        String imageUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){

}
