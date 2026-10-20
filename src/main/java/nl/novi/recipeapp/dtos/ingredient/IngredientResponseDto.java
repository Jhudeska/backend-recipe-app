package nl.novi.recipeapp.dtos.ingredient;

import java.time.LocalDateTime;

public record IngredientResponseDto(
        Long ingredientId,
        String name,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
}
