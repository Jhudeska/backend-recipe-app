package nl.novi.recipeapp.dtos.favorite;

import java.time.LocalDateTime;

public record FavoriteResponseDto(
        Long favoriteId,
        Long recipeId,
        String recipeTitle,
        LocalDateTime created_at
) {
}
