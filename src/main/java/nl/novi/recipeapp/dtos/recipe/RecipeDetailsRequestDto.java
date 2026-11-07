package nl.novi.recipeapp.dtos.recipe;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(
        description = "Gegevens voor het aanmaken of wijzigen van een recept via een formulier"
)
public record RecipeDetailsRequestDto(

        @Schema(
                description = "Titel van het recept",
                example = "Spaghetti Carbonara",
                maxLength = 200,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Recipe title is required")
        @Size(max = 200)
        String title,


        @Schema(
                description = "Receptgegevens zoals titel, beschrijving en bereidingsinstructies",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String description,

        @Schema(
                description = "Unieke identificatie van de categorie waaraan het recept wordt gekoppeld",
        example = "2",
        requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Instructions are required")
        String instructions,

        @Positive(message = "Preparation time must be positive")
        Integer preparationTime,

        @Schema(
                description = "Lijst met ingrediënten en bijbehorende hoeveelheden voor het recept",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @Positive(message = "Servings must be positive")
        Integer servings

) {}
