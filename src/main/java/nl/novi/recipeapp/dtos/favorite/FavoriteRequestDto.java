package nl.novi.recipeapp.dtos.favorite;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Gegevens voor het toevoegen van een recept aan de favorieten")
public record FavoriteRequestDto(

        @Schema(
                description = "Unieke identificatie van het recept dat als favoriet wordt toegevoegd",
                example = "10",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Recipe ID is required")
        @Positive(message = "Recipe ID must be positive")
        Long recipeId

) { }
