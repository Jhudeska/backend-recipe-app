package nl.novi.recipeapp.dtos.recipe;

import nl.novi.recipeapp.dtos.recipeingredient.RecipeIngredientDto;
import nl.novi.recipeapp.entities.RecipeEntity;

import java.util.List;

public record RecipeDetailDto(
        Long recipeId,
        String title,
        String description,
        String instructions,
        Integer preparationTime,
        Integer servings,

        Long categoryId,
        String categoryName,

        Long userId,

        String imageUrl,

        List<RecipeIngredientDto> ingredients,

        Double averageRating,
        Long reviewCount
) { }



