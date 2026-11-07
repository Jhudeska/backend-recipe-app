package nl.novi.recipeapp.dtos.favorite;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Gegevens van een favoriet recept als response")
public record FavoriteResponseDto(

        @Schema(
                description = "Unieke identificatie van de favoriet",
                example = "1"
        )
        Long favoriteId,

        @Schema(
                description = "Unieke identificatie van het recept",
                example = "10"
        )
        Long recipeId,


        @Schema(
                description = "Titel van het favoriete recept",
                example = "Vegetarische pasta"
        )
        String recipeTitle,

        @Schema(
                description = "Datum en tijd waarop het recept als favoriet is toegevoegd",
                example = "2026-11-07T10:30:00"
        )
        LocalDateTime created_at
) {
}
