package nl.novi.recipeapp.dtos;

public record FavoriteDto(
        Long favoriteId,
        Long recipeId,
        String recipeTitle
) { }
