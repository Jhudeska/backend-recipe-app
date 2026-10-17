package nl.novi.recipeapp.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RecipeImageRequestDto(

        @NotBlank(message = "Image URL is required")
        @Size(max = 2048, message = "Image URL cannot exceed 2048 characters")
        String imageUrl,

        @NotNull(message = "Recipe ID is required")
        Long recipeId

) { }
