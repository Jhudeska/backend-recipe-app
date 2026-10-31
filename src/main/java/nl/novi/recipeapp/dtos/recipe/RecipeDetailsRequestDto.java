package nl.novi.recipeapp.dtos.recipe;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RecipeDetailsRequestDto(

        @NotBlank(message = "Recipe title is required")
        @Size(max = 200)
        String title,

        String description,

        @NotBlank(message = "Instructions are required")
        String instructions,

        @Positive(message = "Preparation time must be positive")
        Integer preparationTime,

        @Positive(message = "Servings must be positive")
        Integer servings

) {}
