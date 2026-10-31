package nl.novi.recipeapp.controllers;


import jakarta.validation.Valid;
import nl.novi.recipeapp.dtos.recipe.RecipeFormRequestDto;
import nl.novi.recipeapp.dtos.recipe.RecipeFormResponseDto;
import nl.novi.recipeapp.dtos.recipe.RecipeRequestDto;
import nl.novi.recipeapp.dtos.recipe.RecipeResponseDto;
import nl.novi.recipeapp.services.RecipeFormService;
import nl.novi.recipeapp.services.RecipeSearchService;
import nl.novi.recipeapp.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeSearchService recipeSearchService;
    private final RecipeFormService recipeFormService;

    public RecipeController(RecipeService recipeService, RecipeSearchService recipeSearchService, RecipeFormService recipeFormService) {
        this.recipeService = recipeService;
        this.recipeSearchService = recipeSearchService;
        this.recipeFormService = recipeFormService;
    }

    @GetMapping
    public ResponseEntity<List<RecipeResponseDto>> getAllRecipes() {
        return ResponseEntity
                .ok(recipeService.getAllRecipes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponseDto> getRecipeById(@PathVariable Long id) {
        return ResponseEntity
                .ok(recipeService.getRecipeById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<RecipeResponseDto>> searchRecipes(
            @RequestParam String query
    ) {
        return ResponseEntity.ok(
                recipeSearchService.searchRecipes(query)
        );
    }

    @GetMapping("/form_data")
    public ResponseEntity<RecipeFormResponseDto> getRecipeFormData() {
        return ResponseEntity.ok(
                recipeFormService.getRecipeFormData()
        );
    }

    @PostMapping
    public ResponseEntity<RecipeResponseDto> createRecipe(
            @Valid @RequestBody RecipeFormRequestDto requestDto
    ) {

        RecipeResponseDto newRecipe =
                recipeService.createRecipe(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newRecipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponseDto> updateRecipe(
            @PathVariable Long id,
            @Valid @RequestBody RecipeFormRequestDto requestDto
    ) {

        RecipeResponseDto updatedRecipe =
                recipeService.updateRecipe(id, requestDto);

        return ResponseEntity.ok(updatedRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id){
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
