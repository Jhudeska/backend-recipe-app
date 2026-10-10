package nl.novi.recipeapp.services;

import nl.novi.recipeapp.entities.RecipeEntity;
import nl.novi.recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<RecipeEntity> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public RecipeEntity getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id ));
    }

    public RecipeEntity createRecipe(RecipeEntity recipe) {
        return recipeRepository.save(recipe);
    }

    public RecipeEntity updateRecipe(Long id, RecipeEntity recipe) {
        RecipeEntity existingRecipe = getRecipeById(id);
        existingRecipe.setTitle(recipe.getTitle());
        existingRecipe.setDescription(recipe.getDescription());
        existingRecipe.setInstructions(recipe.getInstructions());
        existingRecipe.setPreparationTime(recipe.getPreparationTime());
        existingRecipe.setServings(recipe.getServings());
        return recipeRepository.save(existingRecipe);
    }

    public void deleteRecipe(Long id) {
        RecipeEntity existingRecipe = getRecipeById(id);
        recipeRepository.delete(existingRecipe);
    }


}
