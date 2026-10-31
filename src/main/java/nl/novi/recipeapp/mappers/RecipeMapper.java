package nl.novi.recipeapp.mappers;

import nl.novi.recipeapp.dtos.recipe.RecipeDetailsRequestDto;
import nl.novi.recipeapp.dtos.recipe.RecipeRequestDto;
import nl.novi.recipeapp.dtos.recipe.RecipeResponseDto;
import nl.novi.recipeapp.dtos.recipeingredient.RecipeIngredientResponseDto;
import nl.novi.recipeapp.entities.RecipeEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeMapper {

    private final RecipeIngredientMapper recipeIngredientMapper;

    public RecipeMapper(
            RecipeIngredientMapper recipeIngredientMapper
    ) {
        this.recipeIngredientMapper = recipeIngredientMapper;
    }

    public RecipeEntity toEntity(RecipeDetailsRequestDto dto) {

        RecipeEntity recipe = new RecipeEntity();

        recipe.setTitle(dto.title());
        recipe.setDescription(dto.description());
        recipe.setInstructions(dto.instructions());
        recipe.setPreparationTime(dto.preparationTime());
        recipe.setServings(dto.servings());

        return recipe;
    }


    public RecipeResponseDto toResponseDto(RecipeEntity recipe) {

        Long categoryId = recipe.getCategory() != null
                ? recipe.getCategory().getCategoryId()
                : null;

        String categoryName = recipe.getCategory() != null
                ? recipe.getCategory().getName()
                : null;

        String imageUrl = recipe.getRecipeImage() != null
                ? recipe.getRecipeImage().getImageUrl()
                : null;

        List<RecipeIngredientResponseDto> ingredients =
                recipe.getIngredients()
                        .stream()
                        .map(recipeIngredientMapper::toResponseDto)
                        .toList();

        return new RecipeResponseDto(
                recipe.getRecipeId(),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getInstructions(),
                recipe.getPreparationTime(),
                recipe.getServings(),
                categoryId,
                categoryName,
                imageUrl,
                ingredients,
                recipe.getCreated_at(),
                recipe.getUpdated_at()
        );
    }
}
