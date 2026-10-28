package nl.novi.recipeapp.dtos.recipe;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import nl.novi.recipeapp.dtos.recipeingredient.RecipeIngredientRequestDto;

import java.util.List;

public record RecipeRequestDto(

        @NotBlank(message = "Recipe title is required")
        @Size(max = 200, message = "Title cannot exceed 200 characters")
        String title,

        String description,

        @NotBlank(message = "Instructions are required")
        String instructions,

        @Positive(message = "Preparation time must be greater than zero")
        Integer preparationTime,

        @NotNull(message = "Servings are required")
        @Positive(message = "Servings must be greater than zero")
        Integer servings,

        @NotNull(message = "Category ID is required")
        Long categoryId

) {
}
