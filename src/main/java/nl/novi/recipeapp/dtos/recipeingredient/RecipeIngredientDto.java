package nl.novi.recipeapp.dtos.recipeingredient;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Gegevens van een ingrediënt dat gekoppeld is aan een recept")
public record RecipeIngredientDto(

        @Schema(
                description = "Unieke identificatie van de recept-ingrediënt koppeling",
                example = "1"
        )
        Long recipeIngredientId,

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
) { }
