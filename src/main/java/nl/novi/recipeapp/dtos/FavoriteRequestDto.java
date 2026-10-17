package nl.novi.recipeapp.dtos;

import jakarta.validation.constraints.NotNull;

public record FavoriteRequestDto(

        @NotNull(message = "Recipe ID is required")
        Long recipeId

) { }
