package nl.novi.recipeapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import nl.novi.recipeapp.dtos.recipeingredient.RecipeIngredientRequestDto;
import nl.novi.recipeapp.dtos.recipeingredient.RecipeIngredientResponseDto;
import nl.novi.recipeapp.entities.RecipeIngredientEntity;
import nl.novi.recipeapp.services.RecipeIngredientService;
import nl.novi.recipeapp.utils.LocationUriBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/recipe_ingredients")
@Tag(name = "Receptingrediënten", description = "API voor het beheren van ingrediënten bij recepten")
public class RecipeIngredientController {

    private final RecipeIngredientService recipeIngredientService;
    private final LocationUriBuilder locationUriBuilder;

    public RecipeIngredientController(
            RecipeIngredientService recipeIngredientService, LocationUriBuilder locationUriBuilder) {
        this.recipeIngredientService = recipeIngredientService;
        this.locationUriBuilder = locationUriBuilder;
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    @Operation(
            summary = "Alle receptingrediënten ophalen",
            description = "Haalt alle ingrediënten op die aan recepten gekoppeld zijn."
    )
    public ResponseEntity<List<RecipeIngredientResponseDto>> getAllRecipeIngredients() {
        return ResponseEntity
                .ok(recipeIngredientService.getAllRecipeIngredients());
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    @Operation(
            summary = "Receptingrediënt ophalen",
            description = "Haalt een specifieke koppeling tussen een recept en een ingrediënt op."
    )
    public ResponseEntity<RecipeIngredientResponseDto> getRecipeIngredientById(
            @PathVariable Long id) {
        return ResponseEntity
                .ok(recipeIngredientService.getRecipeIngredientById(id));
    }

    @GetMapping("/recipe/{recipeId}")
    @PreAuthorize("permitAll()")
    @Operation(
            summary = "Ingrediënten van een recept ophalen",
            description = "Haalt alle ingrediënten op die gekoppeld zijn aan een specifiek recept."
    )
    public ResponseEntity<List<RecipeIngredientResponseDto>> getRecipeIngredientsByRecipeId(
            @PathVariable Long recipeId) {
        return ResponseEntity
                .ok(recipeIngredientService.getRecipeIngredientsByRecipeId(recipeId));
    }

    @GetMapping("/ingredient/{ingredientId}")
    @PreAuthorize("permitAll()")
    @Operation(
            summary = "Recepten van een ingrediënt ophalen",
            description = "Haalt alle recepten op waarin een specifiek ingrediënt wordt gebruikt."
    )
    public ResponseEntity<List<RecipeIngredientResponseDto>> getRecipeIngredientsByIngredientId(
            @PathVariable Long ingredientId) {
        return ResponseEntity
                .ok(recipeIngredientService.getRecipeIngredientsByIngredientId(ingredientId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    @Operation(
            summary = "Ingrediënt aan recept toevoegen",
            description = "Koppelt een ingrediënt aan een recept. Alleen gebruikers met de rol CHEF of ADMIN hebben toegang."
    )
    public ResponseEntity<RecipeIngredientResponseDto> createRecipeIngredient(
            @Valid @RequestBody RecipeIngredientRequestDto requestDto) {

        RecipeIngredientResponseDto newRecipeIngredient =
                recipeIngredientService.createRecipeIngredient(requestDto);

        URI location = locationUriBuilder.buildLocation(
                newRecipeIngredient.ingredientId()
        );

        return ResponseEntity
                .created(location)
                .body(newRecipeIngredient);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    @Operation(
            summary = "Receptingrediënt wijzigen",
            description = "Wijzigt een bestaande koppeling tussen een recept en een ingrediënt. Alleen gebruikers met de rol CHEF of ADMIN hebben toegang."
    )
    public ResponseEntity<RecipeIngredientResponseDto>
    updateRecipeIngredient(
            @PathVariable Long id,
            @Valid @RequestBody RecipeIngredientRequestDto requestDto) {

        return ResponseEntity.ok(
                recipeIngredientService.updateRecipeIngredient(id, requestDto)
        );
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    @Operation(
            summary = "Ingrediënt van recept verwijderen",
            description = "Verwijdert de koppeling tussen een recept en een ingrediënt. Alleen gebruikers met de rol CHEF of ADMIN hebben toegang."
    )
    public ResponseEntity<Void> deleteRecipeIngredient(
            @PathVariable Long id) {
        recipeIngredientService.deleteRecipeIngredient(id);

        return ResponseEntity.noContent().build();
    }
}