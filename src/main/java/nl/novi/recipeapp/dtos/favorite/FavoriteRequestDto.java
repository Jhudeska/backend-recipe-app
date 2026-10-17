package nl.novi.recipeapp.dtos.favorite;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FavoriteRequestDto(

        @NotNull(message = "Recipe ID is required")
        @Positive(message = "Recipe ID must be positive")
        Long recipeId

) { }
