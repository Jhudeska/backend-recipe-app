package nl.novi.recipeapp.services;

import nl.novi.recipeapp.dtos.category.CategoryResponseDto;
import nl.novi.recipeapp.dtos.ingredient.IngredientFormResponseDto;
import nl.novi.recipeapp.dtos.ingredient.IngredientResponseDto;
import nl.novi.recipeapp.dtos.recipe.RecipeFormResponseDto;
import nl.novi.recipeapp.repositories.CategoryRepository;
import nl.novi.recipeapp.repositories.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RecipeFormService {

    private final CategoryRepository categoryRepository;
    private final IngredientRepository ingredientRepository;

    public RecipeFormService(
            CategoryRepository categoryRepository,
            IngredientRepository ingredientRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public RecipeFormResponseDto getRecipeFormData() {

        var categories = categoryRepository.findAll()
                .stream()
                .map(category -> new CategoryResponseDto(
                        category.getCategoryId(),
                        category.getName()
                ))
                .toList();

        var ingredients = ingredientRepository.findAll()
                .stream()
                .map(ingredient -> new IngredientFormResponseDto(
                        ingredient.getIngredientId(),
                        ingredient.getName()
                ))
                .toList();

        return new RecipeFormResponseDto(
                categories,
                ingredients
        );
    }
}