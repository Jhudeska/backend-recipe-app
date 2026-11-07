package nl.novi.recipeapp.dtos.recipeimage;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Gegevens voor het toevoegen of wijzigen van een receptafbeelding")
public record RecipeImageRequestDto(

        @Schema(
                description = "URL van de receptafbeelding",
                example = "https://receta.com/pasta.jpg",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Image URL is required")
        @Size(max = 2048, message = "Image URL cannot exceed 2048 characters")
        String imageUrl,


        @Schema(
                description = "Unieke identificatie van het recept waaraan de afbeelding wordt gekoppeld",
                example = "10",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Recipe ID is required")
        @Positive(message = "Recipe ID must be positive")
        Long recipeId

) { }
