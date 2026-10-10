package nl.novi.recipeapp.controllers;

import nl.novi.recipeapp.entities.IngredientEntity;
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
    public ResponseEntity<List<IngredientEntity>> getAllIngredients() {
        return ResponseEntity.ok(
                ingredientService.getAllIngredients()
        );
    }

    @PostMapping
    public ResponseEntity<IngredientEntity> createIngredient(
            @RequestBody IngredientEntity ingredient) {

        IngredientEntity createdIngredient =
                ingredientService.createIngredient(ingredient);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdIngredient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientEntity> updateIngredient(
            @PathVariable Long id,
            @RequestBody IngredientEntity ingredient) {

        return ResponseEntity.ok(
                ingredientService.updateIngredient(id, ingredient)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(
            @PathVariable Long id) {

        ingredientService.deleteIngredient(id);

        return ResponseEntity.noContent().build();
    }
}
