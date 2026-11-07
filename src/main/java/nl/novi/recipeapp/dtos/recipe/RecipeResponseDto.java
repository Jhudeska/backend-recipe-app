package nl.novi.recipeapp.dtos.recipe;

import io.swagger.v3.oas.annotations.media.Schema;
import nl.novi.recipeapp.dtos.recipeingredient.RecipeIngredientResponseDto;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Gegevens van een recept als response")
public record RecipeResponseDto(

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
                description = "URL van de receptafbeelding",
                example = "https://receta.com/pasta.jpg"
        )
        String imageUrl,

        @Schema(
                description = "Lijst met ingrediënten van het recept"
        )
        List<RecipeIngredientResponseDto> ingredients,

        @Schema(
                description = "Datum en tijd waarop het recept is aangemaakt",
                example = "2026-11-07T10:30:00"
        )
        LocalDateTime createdAt,

        @Schema(
                description = "Datum en tijd waarop het recept voor het laatst is gewijzigd",
                example = "2026-11-07T12:30:00"
        )
        LocalDateTime updatedAt
){

}
