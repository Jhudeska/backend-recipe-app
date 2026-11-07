package nl.novi.recipeapp.dtos.recipeimage;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "Gegevens van een receptafbeelding")
public record RecipeImageDto(
        @Schema(
                description = "Unieke identificatie van de receptafbeelding",
                example = "1"
        )
        Long recipeImageId,

        @Schema(
                description = "URL van de receptafbeelding",
                example = "https://receta.com/pasta.jpg"
        )
        String imageUrl,

        @Schema(
                description = "Unieke identificatie van het recept waartoe de afbeelding behoort",
                example = "10"
        )
        Long recipeId
) { }
