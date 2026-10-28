package nl.novi.recipeapp.controllers;


import jakarta.validation.Valid;
import nl.novi.recipeapp.dtos.recipe.RecipeRequestDto;
import nl.novi.recipeapp.dtos.recipe.RecipeResponseDto;
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

    public RecipeController(RecipeService recipeService, RecipeSearchService recipeSearchService) {
        this.recipeService = recipeService;
        this.recipeSearchService = recipeSearchService;
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

    @PostMapping
    public ResponseEntity<RecipeResponseDto> createRecipe(@Valid @RequestBody RecipeRequestDto recipeRequestDto){
        RecipeResponseDto newRecipe =
                recipeService.createRecipe(recipeRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newRecipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponseDto> updateRecipe(
            @PathVariable Long id,
            @Valid @RequestBody RecipeRequestDto recipeRequestDto){
        RecipeResponseDto updatedRecipe = recipeService.updateRecipe(id, recipeRequestDto);

        return ResponseEntity
                .ok(updatedRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id){
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
