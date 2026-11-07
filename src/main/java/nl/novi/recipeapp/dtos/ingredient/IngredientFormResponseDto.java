package nl.novi.recipeapp.dtos.ingredient;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Beknopte gegevens van een ingrediënt als response voor formulieren")
public record IngredientFormResponseDto(

        @Schema(
                description = "Unieke identificatie van het ingrediënt",
                example = "1"
        )
        Long ingredientId,

        @Schema(
                description = "Naam van het ingrediënt",
                example = "Tomaat"
        )
        String name
) {}
