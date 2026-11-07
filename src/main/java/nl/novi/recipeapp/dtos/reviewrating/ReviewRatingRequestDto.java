package nl.novi.recipeapp.dtos.reviewrating;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
@Schema(description = "Gegevens voor het aanmaken of wijzigen van een receptbeoordeling")
public record ReviewRatingRequestDto(

        @Schema(
                description = "Opmerking of toelichting bij de beoordeling",
                example = "Heerlijk recept, makkelijk te bereiden!"
        )
        @Size(max = 5000, message = "Comment cannot exceed 5000 characters")
        String comment,

        @Schema(
                description = "Beoordelingsscore van het recept, van 1 tot en met 5",
                example = "5",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Rating score is required")
        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating cannot exceed 5")
        Integer ratingScore,

        @Schema(
                description = "Unieke identificatie van het recept dat wordt beoordeeld",
                example = "10",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Recipe ID is required")
        @Min(value = 1, message = "Recipe ID must be positive")
        Long recipeId

) { }
