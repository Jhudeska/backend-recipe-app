package nl.novi.recipeapp.mappers;

import nl.novi.recipeapp.dtos.ingredient.IngredientRequestDto;
import nl.novi.recipeapp.dtos.ingredient.IngredientResponseDto;
import nl.novi.recipeapp.entities.IngredientEntity;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper {

    public IngredientResponseDto toResponseDto(IngredientEntity ingredient) {

        return new IngredientResponseDto(
                ingredient.getIngredientId(),
                ingredient.getName(),
                ingredient.getCreated_at(),
                ingredient.getUpdated_at()
        );
    }

    public IngredientEntity toEntity(IngredientRequestDto dto) {

        IngredientEntity ingredient = new IngredientEntity();
        ingredient.setName(dto.name());

        return ingredient;
    }
}
