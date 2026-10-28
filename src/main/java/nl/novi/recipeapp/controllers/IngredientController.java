package nl.novi.recipeapp.controllers;

import jakarta.validation.Valid;
import nl.novi.recipeapp.dtos.ingredient.IngredientRequestDto;
import nl.novi.recipeapp.dtos.ingredient.IngredientResponseDto;
import nl.novi.recipeapp.services.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ResponseEntity<List<IngredientResponseDto>> getAllIngredients() {
        return ResponseEntity.ok(
                ingredientService.getAllIngredients()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientResponseDto> getIngredientById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ingredientService.getIngredientById(id)
        );
    }

    @PostMapping
    public ResponseEntity<IngredientResponseDto> createIngredient(
            @Valid @RequestBody IngredientRequestDto requestDto) {

        IngredientResponseDto createdIngredient =
                ingredientService.createIngredient(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdIngredient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientResponseDto> updateIngredient(
            @PathVariable Long id,
            @RequestBody IngredientRequestDto requestDto) {

        return ResponseEntity.ok(
                ingredientService.updateIngredient(id, requestDto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(
            @PathVariable Long id) {

        ingredientService.deleteIngredient(id);

        return ResponseEntity.noContent().build();
    }
}
