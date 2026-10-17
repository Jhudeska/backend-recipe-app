package nl.novi.recipeapp.mappers;

import nl.novi.recipeapp.dtos.recipe.RecipeDetailDto;
import nl.novi.recipeapp.dtos.recipe.RecipeDto;
import nl.novi.recipeapp.entities.RecipeEntity;
import org.springframework.stereotype.Component;

@Component
public class RecipeDetailMapper {

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
                imageUrl);
    }

    public RecipeDetailDto toDetailDto(RecipeEntity recipe) {

        return new RecipeDetailDto(
                recipe.getRecipeId(),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getInstructions(),
                recipe.getPreparationTime(),
                recipe.getServings(),
                recipe.getCategory() != null
                        ? recipe.getCategory().getCategoryId()
                        : null,
                recipe.getUser() != null
                        ? recipe.getUser().getUserId()
                        : null
        );
    }
}
