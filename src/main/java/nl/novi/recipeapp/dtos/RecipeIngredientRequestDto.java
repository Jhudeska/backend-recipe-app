package nl.novi.recipeapp.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record RecipeIngredientRequestDto(

        @NotNull(message = "Ingredient ID is required")
        Long ingredientId,

        @NotNull(message = "Quantity is required")
        @DecimalMin(value = "0.0", inclusive = false,
                message = "Quantity must be greater than zero")
        BigDecimal quantity,

        @Size(max = 50, message = "Unit cannot exceed 50 characters")
        String unit

) { }
