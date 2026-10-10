package nl.novi.recipeapp.controllers;


import nl.novi.recipeapp.entities.RecipeEntity;
import nl.novi.recipeapp.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<List<RecipeEntity>> getAllRecipes() {
        return ResponseEntity
                .ok(recipeService.getAllRecipes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeEntity> getRecipeById(@PathVariable Long id) {
        return ResponseEntity
                .ok(recipeService.getRecipeById(id));
    }

    @PostMapping
    public ResponseEntity<RecipeEntity> createRecipe(@RequestBody RecipeEntity recipe){
        RecipeEntity newRecipe = recipeService.createRecipe(recipe);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newRecipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeEntity> updateRecipe(@PathVariable Long id, @RequestBody RecipeEntity recipe){
        RecipeEntity updatedRecipe = recipeService.updateRecipe(id, recipe);

        return ResponseEntity
                .ok(updatedRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id){
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
