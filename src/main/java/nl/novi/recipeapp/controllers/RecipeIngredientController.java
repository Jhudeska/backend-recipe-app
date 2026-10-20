package nl.novi.recipeapp.controllers;

import jakarta.validation.Valid;
import nl.novi.recipeapp.dtos.recipeingredient.RecipeIngredientRequestDto;
import nl.novi.recipeapp.dtos.recipeingredient.RecipeIngredientResponseDto;
import nl.novi.recipeapp.entities.RecipeIngredientEntity;
import nl.novi.recipeapp.services.RecipeIngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    public ResponseEntity<List<RecipeIngredientResponseDto>> getAllRecipeIngredients() {
        return ResponseEntity
                .ok(recipeIngredientService.getAllRecipeIngredients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeIngredientResponseDto> getRecipeIngredientById(
            @PathVariable Long id) {
        return ResponseEntity
                .ok(recipeIngredientService.getRecipeIngredientById(id));
    }

    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<List<RecipeIngredientResponseDto>> getRecipeIngredientsByRecipeId(
            @PathVariable Long recipeId) {
        return ResponseEntity
                .ok(recipeIngredientService.getRecipeIngredientsByRecipeId(recipeId));
    }

    @GetMapping("/ingredient/{ingredientId}")
    public ResponseEntity<List<RecipeIngredientResponseDto>> getRecipeIngredientsByIngredientId(
            @PathVariable Long ingredientId) {
        return ResponseEntity
                .ok(recipeIngredientService.getRecipeIngredientsByIngredientId(ingredientId));
    }

    @PostMapping
    public ResponseEntity<RecipeIngredientResponseDto> createRecipeIngredient(
            @Valid @RequestBody RecipeIngredientRequestDto requestDto) {

        RecipeIngredientResponseDto newRecipeIngredient =
                recipeIngredientService.createRecipeIngredient(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newRecipeIngredient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeIngredientResponseDto>
    updateRecipeIngredient(
            @PathVariable Long id,
            @Valid @RequestBody RecipeIngredientRequestDto requestDto) {

        return ResponseEntity.ok(
                recipeIngredientService.updateRecipeIngredient(id, requestDto)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipeIngredient(
            @PathVariable Long id) {
        recipeIngredientService.deleteRecipeIngredient(id);

        return ResponseEntity.noContent().build();
    }
}