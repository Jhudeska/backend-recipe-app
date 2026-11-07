package nl.novi.recipeapp.dtos.ingredient;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Gegevens van een ingrediënt als response")
public record IngredientResponseDto(

        @Schema(
                description = "Unieke identificatie van het ingrediënt",
                example = "1"
        )
        Long ingredientId,

        @Schema(
                description = "Naam van het ingrediënt",
                example = "Tomaat"
        )
        String name,

        @Schema(
                description = "Datum en tijd waarop het ingrediënt is aangemaakt",
                example = "2026-11-07T10:30:00"
        )
        LocalDateTime created_at,

        @Schema(
                description = "Datum en tijd waarop het ingrediënt voor het laatst is gewijzigd",
                example = "2026-11-07T12:30:00"
        )
        LocalDateTime updated_at
) {
}
