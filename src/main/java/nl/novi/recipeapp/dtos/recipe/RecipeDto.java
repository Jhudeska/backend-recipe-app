package nl.novi.recipeapp.dtos.recipe;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Beknopte gegevens van een recept")
public record RecipeDto(

        @Schema(
                description = "Unieke identificatie van het recept",
                example = "1"
        )
        Long recipeId,

        @Schema(
                description = "Naam van het recept",
                example = "Spaghetti Carbonara"
        )
        String title,

        @Schema(
                description = "Beschrijving van het recept",
                example = "Een klassieke Italiaanse pasta"
        )
        String description,

        @Schema(
                description = "Bereidingstijd in minuten",
                example = "30"
        )
        Integer preparationTime,

        @Schema(
                description = "Aantal personen waarvoor het recept bedoeld is",
                example = "4"
        )
        Integer servings,


        @Schema(
                description = "URL van de receptafbeelding",
                example = "https://example.com/recipe.jpg"
        )
        String imageUrl
) { }