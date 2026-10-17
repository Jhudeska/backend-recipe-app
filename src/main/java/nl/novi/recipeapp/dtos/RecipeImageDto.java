package nl.novi.recipeapp.dtos;

public record RecipeImageDto(
        Long recipeImageId,
        String imageUrl,
        Long recipeId
) { }
