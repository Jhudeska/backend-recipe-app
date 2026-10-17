package nl.novi.recipeapp.controllers;

import jakarta.validation.Valid;
import nl.novi.recipeapp.dtos.recipeimage.RecipeImageRequestDto;
import nl.novi.recipeapp.dtos.recipeimage.RecipeImageResponseDto;
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
    public ResponseEntity<List<RecipeImageResponseDto>> getAllRecipeImages() {
        return ResponseEntity.ok(
                recipeImageService.getAllRecipeImages()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeImageResponseDto> getRecipeImageById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                recipeImageService.getRecipeImageById(id)
        );
    }

    @PostMapping
    public ResponseEntity<RecipeImageResponseDto> createRecipeImage(
            @Valid @RequestBody RecipeImageRequestDto requestDto) {

        RecipeImageResponseDto createdRecipeImage =
                recipeImageService.createRecipeImage(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdRecipeImage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeImageResponseDto> updateRecipeImage(
            @PathVariable Long id,
            @Valid @RequestBody RecipeImageRequestDto requestDto) {

        return ResponseEntity.ok(
                recipeImageService.updateRecipeImage(id, requestDto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipeImage(
            @PathVariable Long id) {

        recipeImageService.deleteRecipeImage(id);

        return ResponseEntity.noContent().build();
    }
}