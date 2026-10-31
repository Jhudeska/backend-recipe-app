package nl.novi.recipeapp.dtos.recipeingredient;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RecipeIngredientResponseDto(

        Long ingredientId,
        String ingredientName,
        BigDecimal quantity,
        String unit
) {
}
