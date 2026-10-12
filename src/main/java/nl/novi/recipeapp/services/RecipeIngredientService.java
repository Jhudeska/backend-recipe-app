package nl.novi.recipeapp.services;

import nl.novi.recipeapp.entities.IngredientEntity;
import nl.novi.recipeapp.entities.RecipeEntity;
import nl.novi.recipeapp.entities.RecipeIngredientEntity;
import nl.novi.recipeapp.repositories.IngredientRepository;
import nl.novi.recipeapp.repositories.RecipeIngredientRepository;
import nl.novi.recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public RecipeIngredientService(
            RecipeIngredientRepository recipeIngredientRepository,
            RecipeRepository recipeRepository,
            IngredientRepository ingredientRepository) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public List<RecipeIngredientEntity> getAllRecipeIngredients() {
        return recipeIngredientRepository.findAll();
    }

    public RecipeIngredientEntity getRecipeIngredientById(Long id) {
        return recipeIngredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Recipe ingredient not found with id: " + id));
    }

    public List<RecipeIngredientEntity> getRecipeIngredientsByRecipeId(Long recipeId) {
        return recipeIngredientRepository.findByRecipe_RecipeId(recipeId);
    }

    public List<RecipeIngredientEntity> getRecipeIngredientsByIngredientId(Long ingredientId) {
        return recipeIngredientRepository.findByIngredient_IngredientId(ingredientId);
    }

    public RecipeIngredientEntity createRecipeIngredient(
            Long recipeId, Long ingredientId,
            Double quantity, String unit) {

        RecipeEntity recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException(
                        "Recipe not found with id: " + recipeId));

        IngredientEntity ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new RuntimeException(
                        "Ingredient not found with id: " + ingredientId));

        RecipeIngredientEntity recipeIngredient = new RecipeIngredientEntity();
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setQuantity(quantity);
        recipeIngredient.setUnit(unit);

        return recipeIngredientRepository.save(recipeIngredient);
    }

    public void deleteRecipeIngredient(Long id) {
        RecipeIngredientEntity existingRecipeIngredient =
                getRecipeIngredientById(id);

        recipeIngredientRepository.delete(existingRecipeIngredient);
    }
}