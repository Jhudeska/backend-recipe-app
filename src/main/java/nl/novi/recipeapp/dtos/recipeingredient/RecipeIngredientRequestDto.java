package nl.novi.recipeapp.dtos.recipeingredient;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record RecipeIngredientRequestDto(

        @NotNull(message = "Recipe ID is required")
        @Positive(message = "Recipe ID must be positive")
        Long recipeId,

        @NotNull(message = "Ingredient ID is required")
        @Positive(message = "Ingredient ID must be positive")
        Long ingredientId,

        @NotNull(message = "Quantity is required")
        @DecimalMin(value = "0.0", inclusive = false,
                message = "Quantity must be greater than zero")
        BigDecimal quantity,

        @Size(max = 50, message = "Unit cannot exceed 50 characters")
        String unit

) { }
