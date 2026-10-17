package nl.novi.recipeapp.mappers;

import nl.novi.recipeapp.dtos.RecipeImageDto;
import nl.novi.recipeapp.entities.RecipeImageEntity;
import org.springframework.stereotype.Component;

@Component
public class RecipeImageMapper {

    public RecipeImageDto toDto(RecipeImageEntity recipeImage) {

        return new RecipeImageDto(
                recipeImage.getRecipeImageId(),
                recipeImage.getImageUrl(),
                recipeImage.getRecipe().getRecipeId()
        );
    }
}
