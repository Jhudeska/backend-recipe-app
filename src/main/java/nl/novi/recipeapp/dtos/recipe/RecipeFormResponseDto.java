package nl.novi.recipeapp.dtos.recipe;

import nl.novi.recipeapp.dtos.category.CategoryResponseDto;
import nl.novi.recipeapp.dtos.ingredient.IngredientFormResponseDto;
import nl.novi.recipeapp.dtos.ingredient.IngredientResponseDto;

import java.util.List;

public record RecipeFormResponseDto(

        List<CategoryResponseDto> categories,

        List<IngredientFormResponseDto> ingredients

) {}
