package nl.novi.recipeapp.dtos.recipeingredient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import nl.novi.recipeapp.dtos.recipe.RecipeDetailsRequestDto;

import java.math.BigDecimal;
import java.util.List;

public record RecipeIngredientFormDto(
        @NotNull(message = "Ingredient ID is required")
        @Positive(message = "Ingredient ID must be positive")
        Long ingredientId,

        @NotNull(message = "Quantity is required")
        @DecimalMin(value = "0.0", inclusive = false,
                message = "Quantity must be greater than zero")
        BigDecimal quantity,

        @Size(max = 50, message = "Unit cannot exceed 50 characters")
        String unit


) {

}
