package nl.novi.recipeapp.services;

import nl.novi.recipeapp.dtos.RecipeDto;
import nl.novi.recipeapp.entities.RecipeEntity;
import nl.novi.recipeapp.mappers.RecipeMapper;
import nl.novi.recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    public RecipeService(RecipeRepository recipeRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }

    public List<RecipeDto> getAllRecipes() {
        return recipeRepository.findAll()
                .stream()
                .map(recipeMapper::toOverviewDto)
                .toList();
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
