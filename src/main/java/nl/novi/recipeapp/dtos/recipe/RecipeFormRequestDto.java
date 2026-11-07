package nl.novi.recipeapp.dtos.recipe;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import nl.novi.recipeapp.dtos.recipeingredient.RecipeIngredientFormDto;

import java.util.List;

@Schema(description = "Gegevens voor het aanmaken of wijzigen van een recept via een formulier")
public record RecipeFormRequestDto(

        @Schema(
                description = "Receptgegevens zoals titel, beschrijving en bereidingsinstructies",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @Valid
        @NotNull
        RecipeDetailsRequestDto recipe,


        @Schema(
                description = "Unieke identificatie van de categorie waaraan het recept wordt gekoppeld",
                example = "2",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull
        @Positive
        Long categoryId,


        @Schema(
                description = "Lijst met ingrediënten en bijbehorende hoeveelheden voor het recept",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @Valid
        List<@NotNull RecipeIngredientFormDto> ingredients
) {}

