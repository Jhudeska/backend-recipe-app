package nl.novi.recipeapp.dtos.category;

import io.swagger.v3.oas.annotations.media.Schema;

public record CategoryResponseDto(

        @Schema(
                description = "Unieke identificatie van de categorie",
                example = "1"
        )
        Long categoryId,

        @Schema(
                description = "Naam van de categorie",
                example = "Vegetarisch"
        )
        String name
) {
}
