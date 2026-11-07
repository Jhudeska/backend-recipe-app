package nl.novi.recipeapp.dtos.recipe;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import nl.novi.recipeapp.dtos.recipeingredient.RecipeIngredientRequestDto;

import java.util.List;

@Schema(description = "Gegevens voor het aanmaken of wijzigen van een recept")
public record RecipeRequestDto(

        @Schema(
                description = "Titel van het recept",
                example = "Vegetarische pasta",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Recipe title is required")
        @Size(max = 200, message = "Title cannot exceed 200 characters")
        String title,

        @Schema(
                description = "Beschrijving van het recept",
                example = "Een heerlijke vegetarische pasta met verse groenten"
        )
        String description,

        @Schema(
                description = "Bereidingsinstructies van het recept",
                example = "Kook de pasta en voeg de gebakken groenten toe.",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Instructions are required")
        String instructions,

        @Schema(
                description = "Bereidingstijd van het recept in minuten",
                example = "30"
        )
        @Positive(message = "Preparation time must be greater than zero")
        Integer preparationTime,

        @Schema(
                description = "Aantal personen waarvoor het recept bedoeld is",
                example = "4",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Servings are required")
        @Positive(message = "Servings must be greater than zero")
        Integer servings,

        @Schema(
                description = "Unieke identificatie van de categorie waaraan het recept wordt gekoppeld",
                example = "2",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Category ID is required")
        Long categoryId

) {
}
