package nl.novi.recipeapp.dtos.ingredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record IngredientRequestDto(

        @NotBlank(message = "Ingredient name is required")
        @Size(max = 255, message = "Ingredient name cannot exceed 255 characters")
        String name

) { }

