package nl.novi.recipeapp.mappers;

import nl.novi.recipeapp.dtos.ingredient.IngredientDto;
import nl.novi.recipeapp.dtos.ingredient.IngredientRequestDto;
import nl.novi.recipeapp.entities.IngredientEntity;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper {

    public IngredientDto toDto(IngredientEntity ingredient) {

        return new IngredientDto(
                ingredient.getIngredientId(),
                ingredient.getName()
        );
    }

    public IngredientEntity toEntity(IngredientRequestDto dto) {

        IngredientEntity ingredient = new IngredientEntity();
        ingredient.setName(dto.name());

        return ingredient;
    }
}
