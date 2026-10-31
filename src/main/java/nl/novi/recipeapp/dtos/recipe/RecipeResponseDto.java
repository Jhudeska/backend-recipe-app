package nl.novi.recipeapp.dtos.recipe;

import nl.novi.recipeapp.dtos.recipeingredient.RecipeIngredientResponseDto;

import java.time.LocalDateTime;
import java.util.List;

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
        List<RecipeIngredientResponseDto> ingredients,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){

}
