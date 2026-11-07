package nl.novi.recipeapp.dtos.recipe;

import io.swagger.v3.oas.annotations.media.Schema;
import nl.novi.recipeapp.dtos.recipeingredient.RecipeIngredientDto;
import nl.novi.recipeapp.entities.RecipeEntity;

import java.util.List;

@Schema(description = "Gegevens van een recept als response")
public record RecipeDetailDto(

        @Schema(
                description = "Unieke identificatie van het recept",
                example = "1"
        )
        Long recipeId,

        @Schema(
                description = "Titel van het recept",
                example = "Vegetarische pasta"
        )
        String title,

        @Schema(
                description = "Beschrijving van het recept",
                example = "Een heerlijke vegetarische pasta met verse groenten"
        )
        String description,

        @Schema(
                description = "Bereidingsinstructies van het recept",
                example = "Kook de pasta en voeg de gebakken groenten toe."
        )
        String instructions,

        @Schema(
                description = "Bereidingstijd van het recept in minuten",
                example = "30"
        )
        Integer preparationTime,

        @Schema(
                description = "Aantal personen waarvoor het recept bedoeld is",
                example = "4"
        )
        Integer servings,

        @Schema(
                description = "Unieke identificatie van de categorie",
                example = "2"
        )
        Long categoryId,

        @Schema(
                description = "Naam van de categorie",
                example = "Vegetarisch"
        )
        String categoryName,

        @Schema(
                description = "Unieke identificatie van de gebruiker die het recept heeft aangemaakt",
                example = "5"
        )
        Long userId,

        @Schema(
                description = "URL van de receptafbeelding",
                example = "https://receta.com/pasta.jpg"
        )
        String imageUrl,

        @Schema(
                description = "Lijst met ingrediënten van het recept"
        )
        List<RecipeIngredientDto> ingredients,

        @Schema(
                description = "Gemiddelde beoordeling van het recept",
                example = "4.5"
        )
        Double averageRating,

        @Schema(
                description = "Aantal beoordelingen van het recept",
                example = "12"
        )
        Long reviewCount
) { }



