package nl.novi.recipeapp.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RecipeRequestDto(

        @NotBlank(message = "Recipe title is required")
        @Size(max = 200, message = "Title cannot exceed 200 characters")
        String title,

        String description,

        @NotBlank(message = "Instructions are required")
        String instructions,

        @Positive(message = "Preparation time must be greater than zero")
        Integer preparationTime,

        @Positive(message = "Servings must be greater than zero")
        Integer servings,

        Long categoryId

) { }
