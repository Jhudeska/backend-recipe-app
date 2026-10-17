package nl.novi.recipeapp.dtos.recipe;

public record RecipeDto(
        Long recipeId,
        String title,
        String description,
        Integer preparationTime,
        Integer servings,
        String imageUrl
) { }