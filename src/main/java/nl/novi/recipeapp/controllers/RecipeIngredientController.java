package nl.novi.recipeapp.controllers;

import nl.novi.recipeapp.entities.RecipeIngredientEntity;
import nl.novi.recipeapp.services.RecipeIngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipe_ingredients")
public class RecipeIngredientController {

    private final RecipeIngredientService recipeIngredientService;

    public RecipeIngredientController(
            RecipeIngredientService recipeIngredientService) {
        this.recipeIngredientService = recipeIngredientService;
    }

    @GetMapping
    public ResponseEntity<List<RecipeIngredientEntity>> getAllRecipeIngredients() {
        return ResponseEntity
                .ok(recipeIngredientService.getAllRecipeIngredients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeIngredientEntity> getRecipeIngredientById(
            @PathVariable Long id) {
        return ResponseEntity
                .ok(recipeIngredientService.getRecipeIngredientById(id));
    }

    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<List<RecipeIngredientEntity>> getRecipeIngredientsByRecipeId(
            @PathVariable Long recipeId) {
        return ResponseEntity
                .ok(recipeIngredientService.getRecipeIngredientsByRecipeId(recipeId));
    }

    @GetMapping("/ingredient/{ingredientId}")
    public ResponseEntity<List<RecipeIngredientEntity>> getRecipeIngredientsByIngredientId(
            @PathVariable Long ingredientId) {
        return ResponseEntity
                .ok(recipeIngredientService.getRecipeIngredientsByIngredientId(ingredientId));
    }

    @PostMapping
    public ResponseEntity<RecipeIngredientEntity> createRecipeIngredient(
            @RequestParam Long recipeId,
            @RequestParam Long ingredientId,
            @RequestParam Double quantity,
            @RequestParam String unit) {

        RecipeIngredientEntity newRecipeIngredient =
                recipeIngredientService.createRecipeIngredient(
                        recipeId, ingredientId, quantity, unit);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newRecipeIngredient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipeIngredient(
            @PathVariable Long id) {
        recipeIngredientService.deleteRecipeIngredient(id);

        return ResponseEntity.noContent().build();
    }
}