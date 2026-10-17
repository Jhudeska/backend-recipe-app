package nl.novi.recipeapp.dtos;

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
