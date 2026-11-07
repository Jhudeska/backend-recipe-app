package nl.novi.recipeapp.dtos.recipeingredient;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Gegevens van een ingrediënt binnen een recept als response")
public record RecipeIngredientResponseDto(

        @Schema(
                description = "Unieke identificatie van het ingrediënt",
                example = "5"
        )
        Long ingredientId,

        @Schema(
                description = "Naam van het ingrediënt",
                example = "Tomaat"
        )
        String ingredientName,

        @Schema(
                description = "Hoeveelheid van het ingrediënt",
                example = "250.0"
        )
        BigDecimal quantity,

        @Schema(
                description = "Eenheid waarin de hoeveelheid wordt uitgedrukt",
                example = "gram"
        )
        String unit
) {
}
