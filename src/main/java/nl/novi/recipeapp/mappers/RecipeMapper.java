package nl.novi.recipeapp.mappers;

import nl.novi.recipeapp.dtos.RecipeDto;
import nl.novi.recipeapp.entities.RecipeEntity;
import org.springframework.stereotype.Component;

@Component
public class RecipeMapper {

    public RecipeDto toOverviewDto(RecipeEntity recipe) {
        String imageUrl = recipe.getRecipeImage() != null
                ? recipe.getRecipeImage().getImageUrl()
                : null;

        return new RecipeDto(
                recipe.getRecipeId(),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getPreparationTime(),
                recipe.getServings(),
                imageUrl
        );
    }
}
