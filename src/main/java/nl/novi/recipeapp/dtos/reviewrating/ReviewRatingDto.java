package nl.novi.recipeapp.dtos.reviewrating;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Gegevens van een receptbeoordeling")
public record ReviewRatingDto(

        @Schema(
                description = "Unieke identificatie van de beoordeling",
                example = "1"
        )
        Long reviewRatingId,

        @Schema(
                description = "Opmerking of toelichting bij de beoordeling",
                example = "Heerlijk recept, makkelijk te bereiden!"
        )
        String comment,


        @Schema(
                description = "Beoordelingsscore van het recept, van 1 tot en met 5",
                example = "5"
        )
        Integer ratingScore,


        @Schema(
                description = "Unieke identificatie van het beoordeelde recept",
                example = "10"
        )
        Long recipeId,

        @Schema(
                description = "Titel van het beoordeelde recept",
                example = "Vegetarische pasta"
        )
        String recipeTitle,

        @Schema(
                description = "Unieke identificatie van de gebruiker die de beoordeling heeft gegeven",
                example = "5"
        )
        Long userId
) { }
