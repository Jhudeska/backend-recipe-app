package nl.novi.recipeapp.dtos.recipeimage;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record RecipeImageResponseDto(

        @Schema(
                description = "Unieke identificatie van de receptafbeelding",
                example = "1"
        )
        Long recipeImageId,

        @Schema(
                description = "URL van de receptafbeelding",
                example = "https://receta.com/images/carbonara.jpg"
        )
        String imageUrl,

        @Schema(
                description = "Identificatie van het recept waaraan de afbeelding gekoppeld is",
                example = "1"
        )
        Long recipeId,

        @Schema(
                description = "Datum en tijd waarop de afbeelding is aangemaakt",
                example = "2026-11-07T14:30:00"
        )
        LocalDateTime created_at,

        @Schema(
                description = "Datum en tijd waarop de afbeelding voor het laatst is bijgewerkt",
                example = "2026-11-07T15:30:00"
        )
        LocalDateTime updated_at
) {
}
