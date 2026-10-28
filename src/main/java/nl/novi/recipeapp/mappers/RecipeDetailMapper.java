package nl.novi.recipeapp.mappers;

import nl.novi.recipeapp.dtos.recipe.RecipeDetailDto;
import nl.novi.recipeapp.dtos.recipe.RecipeDto;
import nl.novi.recipeapp.dtos.recipeingredient.RecipeIngredientDto;
import nl.novi.recipeapp.entities.RecipeEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeDetailMapper {

    public  RecipeDetailDto toDetailDto(RecipeEntity recipe) {

        Long categoryId = recipe.getCategory() != null
                ? recipe.getCategory().getCategoryId()
                : null;

        String categoryName = recipe.getCategory() != null
                ? recipe.getCategory().getName()
                : null;

        String imageUrl = recipe.getRecipeImage() != null
                ? recipe.getRecipeImage().getImageUrl()
                : null;

        List<RecipeIngredientDto> ingredients =
                recipe.getIngredients()
                        .stream()
                        .map(ingredient -> new RecipeIngredientDto(
                                ingredient.getRecipeIngredientId(),
                                ingredient.getIngredient().getIngredientId(),
                                ingredient.getIngredient().getName(),
                                ingredient.getQuantity(),
                                ingredient.getUnit()
                        ))
                        .toList();

        return new RecipeDetailDto(
                recipe.getRecipeId(),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getInstructions(),
                recipe.getPreparationTime(),
                recipe.getServings(),
                categoryId,
                categoryName,
                recipe.getUser() != null
                        ? recipe.getUser().getUserId()
                        : null,
                imageUrl,
                ingredients,
                null,
                null
        );
    }
}
