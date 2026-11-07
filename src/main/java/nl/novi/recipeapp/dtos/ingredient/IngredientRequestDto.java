package nl.novi.recipeapp.dtos.ingredient;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Gegevens voor het aanmaken of wijzigen van een ingrediënt")
public record IngredientRequestDto(

        @Schema(
                description = "Naam van het ingrediënt",
                example = "Tomaat",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Ingredient name is required")
        @Size(max = 255, message = "Ingredient name cannot exceed 255 characters")
        String name

) { }

