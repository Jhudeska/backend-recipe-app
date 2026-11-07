package nl.novi.recipeapp.dtos.recipeingredient;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Gegevens voor het koppelen van een ingrediënt aan een recept")
public record RecipeIngredientRequestDto(

        @Schema(
                description = "Unieke identificatie van het recept",
                example = "10",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Recipe ID is required")
        @Positive(message = "Recipe ID must be positive")
        Long recipeId,


        @Schema(
                description = "Unieke identificatie van het ingrediënt",
                example = "5",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Ingredient ID is required")
        @Positive(message = "Ingredient ID must be positive")
        Long ingredientId,


        @Schema(
                description = "Hoeveelheid van het ingrediënt",
                example = "250.0",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Quantity is required")
        @DecimalMin(value = "0.0", inclusive = false,
                message = "Quantity must be greater than zero")
        BigDecimal quantity,


        @Schema(
                description = "Eenheid waarin de hoeveelheid wordt uitgedrukt",
                example = "gram"
        )
        @Size(max = 50, message = "Unit cannot exceed 50 characters")
        String unit

) { }


