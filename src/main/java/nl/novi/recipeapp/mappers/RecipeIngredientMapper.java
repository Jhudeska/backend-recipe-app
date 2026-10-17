package nl.novi.recipeapp.mappers;

import nl.novi.recipeapp.dtos.recipeingredient.RecipeIngredientDto;
import nl.novi.recipeapp.entities.RecipeIngredientEntity;
import org.springframework.stereotype.Component;

@Component
public class RecipeIngredientMapper {

    public RecipeIngredientDto toDto(RecipeIngredientEntity recipeIngredient) {

        return new RecipeIngredientDto(
                recipeIngredient.getRecipeIngredientId(),
                recipeIngredient.getIngredient().getIngredientId(),
                recipeIngredient.getIngredient().getName(),
                recipeIngredient.getQuantity(),
                recipeIngredient.getUnit()
        );
    }
}
