package nl.novi.recipeapp.dtos.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Gegevens voor het aanmaken of wijzigen van een categorie")
public record CategoryRequestDto(

        @Schema(
                description = "Naam van de categorie",
                example = "Vegetarisch",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Category name is required")
        @Size(max = 100, message = "Category name cannot exceed 100 characters")
        String name,

        @Schema(
                description = "Beschrijving van de categorie",
                example = "Recepten zonder vlees of vis"
        )
        String description

) { }