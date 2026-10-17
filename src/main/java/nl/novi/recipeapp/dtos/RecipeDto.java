package nl.novi.recipeapp.dtos;

public record RecipeDto(
        Long recipeId,
        String title,
        String description,
        Integer preparationTime,
        Integer servings,
        String imageUrl
) { }