package nl.novi.recipeapp.controllers;

import nl.novi.recipeapp.entities.RecipeImageEntity;
import nl.novi.recipeapp.services.RecipeImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipe_images")
public class RecipeImageController {

    private final RecipeImageService recipeImageService;

    public RecipeImageController(
            RecipeImageService recipeImageService) {
        this.recipeImageService = recipeImageService;
    }

    @GetMapping
    public ResponseEntity<List<RecipeImageEntity>> getAllRecipeImages() {
        return ResponseEntity.ok(
                recipeImageService.getAllRecipeImages()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeImageEntity> getRecipeImageById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                recipeImageService.getRecipeImageById(id)
        );
    }

    @PostMapping
    public ResponseEntity<RecipeImageEntity> createRecipeImage(
            @RequestBody RecipeImageEntity recipeImage) {

        RecipeImageEntity createdRecipeImage =
                recipeImageService.createRecipeImage(recipeImage);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdRecipeImage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeImageEntity> updateRecipeImage(
            @PathVariable Long id,
            @RequestBody RecipeImageEntity recipeImage) {

        return ResponseEntity.ok(
                recipeImageService.updateRecipeImage(id, recipeImage)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipeImage(
            @PathVariable Long id) {

        recipeImageService.deleteRecipeImage(id);

        return ResponseEntity.noContent().build();
    }
}