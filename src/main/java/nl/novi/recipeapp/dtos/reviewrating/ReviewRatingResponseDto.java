package nl.novi.recipeapp.dtos.reviewrating;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Gegevens van een beoordeling als response")
public record ReviewRatingResponseDto(

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
                description = "Score die de gebruiker aan het recept heeft gegeven",
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
        Long userId,

        @Schema(
                description = "Datum en tijd waarop de beoordeling is aangemaakt",
                example = "2026-11-07T10:30:00"
        )
        LocalDateTime created_at,

        @Schema(
                description = "Datum en tijd waarop de beoordeling voor het laatst is gewijzigd",
                example = "2026-11-07T12:30:00"
        )
        LocalDateTime updated_at
) {
}
