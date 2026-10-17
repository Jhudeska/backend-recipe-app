package nl.novi.recipeapp.dtos.favorite;

public record FavoriteDto(
        Long favoriteId,
        Long recipeId,
        String recipeTitle
) { }
