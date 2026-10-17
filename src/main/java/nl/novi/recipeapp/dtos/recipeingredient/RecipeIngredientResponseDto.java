package nl.novi.recipeapp.dtos.recipeingredient;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RecipeIngredientResponseDto(
        Long recipeIngredientId,
        Long recipeId,
        Long ingredientId,
        String ingredientName,
        BigDecimal quantity,
        String unit,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
}
