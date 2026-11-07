package nl.novi.recipeapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import nl.novi.recipeapp.dtos.ingredient.IngredientRequestDto;
import nl.novi.recipeapp.dtos.ingredient.IngredientResponseDto;
import nl.novi.recipeapp.services.IngredientService;
import nl.novi.recipeapp.utils.LocationUriBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ingredients")
@Tag(name = "Ingrediënten", description = "API voor het beheren van ingrediënten")
public class IngredientController {

    private final IngredientService ingredientService;
    private final LocationUriBuilder locationUriBuilder;

    public IngredientController(IngredientService ingredientService, LocationUriBuilder locationUriBuilder) {
        this.ingredientService = ingredientService;
        this.locationUriBuilder = locationUriBuilder;
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    @Operation(
            summary = "Alle ingrediënten ophalen",
            description = "Haalt alle beschikbare ingrediënten uit de database op."
    )
    public ResponseEntity<List<IngredientResponseDto>> getAllIngredients() {
        return ResponseEntity.ok(
                ingredientService.getAllIngredients()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    @Operation(
            summary = "Ingrediënt ophalen",
            description = "Haalt een specifiek ingrediënt op aan de hand van het opgegeven ID."
    )
    public ResponseEntity<IngredientResponseDto> getIngredientById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ingredientService.getIngredientById(id)
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    @Operation(
            summary = "Nieuw ingrediënt toevoegen",
            description = "Voegt een nieuw ingrediënt toe. Alleen gebruikers met de rol CHEF of ADMIN hebben toegang."
    )
    public ResponseEntity<IngredientResponseDto> createIngredient(
            @Valid @RequestBody IngredientRequestDto requestDto) {

        IngredientResponseDto createdIngredient =
                ingredientService.createIngredient(requestDto);

        URI location = locationUriBuilder.buildLocation(
                createdIngredient.ingredientId()
        );

        return ResponseEntity
                .created(location)
                .body(createdIngredient);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    @Operation(
            summary = "Ingrediënt wijzigen",
            description = "Wijzigt een bestaand ingrediënt op basis van het opgegeven ID. Alleen gebruikers met de rol CHEF of ADMIN hebben toegang."
    )
    public ResponseEntity<IngredientResponseDto> updateIngredient(
            @PathVariable Long id,
            @RequestBody IngredientRequestDto requestDto) {

        return ResponseEntity.ok(
                ingredientService.updateIngredient(id, requestDto)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    @Operation(
            summary = "Ingrediënt verwijderen",
            description = "Verwijdert een bestaand ingrediënt op basis van het opgegeven ID. Alleen gebruikers met de rol CHEF of ADMIN hebben toegang."
    )
    public ResponseEntity<Void> deleteIngredient(
            @PathVariable Long id) {

        ingredientService.deleteIngredient(id);

        return ResponseEntity.noContent().build();
    }
}
