package nl.novi.recipeapp.dtos.recipe;

public record RecipeDetailDto(
        Long recipeId,
        String title,
        String description,
        String instructions,
        Integer preparationTime,
        Integer servings,
        Long categoryId,
        Long userId
) { }
