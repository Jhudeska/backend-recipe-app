package nl.novi.recipeapp.dtos.recipeimage;

public record RecipeImageDto(
        Long recipeImageId,
        String imageUrl,
        Long recipeId
) { }
