package nl.novi.recipeapp.dtos;

import java.math.BigDecimal;

public record RecipeIngredientDto(
        Long recipeIngredientId,
        Long ingredientId,
        String ingredientName,
        BigDecimal quantity,
        String unit
) { }
