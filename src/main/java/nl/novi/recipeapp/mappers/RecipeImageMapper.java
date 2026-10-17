package nl.novi.recipeapp.mappers;

import nl.novi.recipeapp.dtos.recipeimage.RecipeImageDto;
import nl.novi.recipeapp.dtos.recipeimage.RecipeImageResponseDto;
import nl.novi.recipeapp.entities.RecipeImageEntity;
import org.springframework.stereotype.Component;

@Component
public class RecipeImageMapper {

    public RecipeImageResponseDto toResponseDto(RecipeImageEntity recipeImage) {

        return new RecipeImageResponseDto(
                recipeImage.getRecipeImageId(),
                recipeImage.getImageUrl(),
                recipeImage.getRecipe().getRecipeId(),
                recipeImage.getCreated_at(),
                recipeImage.getUpdated_at()
        );
    }
}
