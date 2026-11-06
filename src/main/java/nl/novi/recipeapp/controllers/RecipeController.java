package nl.novi.recipeapp.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import nl.novi.recipeapp.dtos.recipe.RecipeFormRequestDto;
import nl.novi.recipeapp.dtos.recipe.RecipeFormResponseDto;
import nl.novi.recipeapp.dtos.recipe.RecipeRequestDto;
import nl.novi.recipeapp.dtos.recipe.RecipeResponseDto;
import nl.novi.recipeapp.services.RecipeFormService;
import nl.novi.recipeapp.services.RecipeSearchService;
import nl.novi.recipeapp.services.RecipeService;
import nl.novi.recipeapp.utils.LocationUriBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipes")
@Tag(name = "Recepten", description = "Api voor het beheren van recepten")
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeSearchService recipeSearchService;
    private final RecipeFormService recipeFormService;
    private final LocationUriBuilder locationUriBuilder;

    public RecipeController(RecipeService recipeService, RecipeSearchService recipeSearchService, RecipeFormService recipeFormService, LocationUriBuilder locationUriBuilder) {
        this.recipeService = recipeService;
        this.recipeSearchService = recipeSearchService;
        this.recipeFormService = recipeFormService;
        this.locationUriBuilder = locationUriBuilder;
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    @Operation(
            summary = "Alle recepten ophalen",
            description = "Haalt alle beschikbare recepten uit de database op."
    )
    public ResponseEntity<List<RecipeResponseDto>> getAllRecipes() {
        return ResponseEntity
                .ok(recipeService.getAllRecipes());
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    @Operation(
            summary = "Recept ophalen",
            description = "Haalt een specifiek recept op aan de hand van het opgegeven ID.")
    public ResponseEntity<RecipeResponseDto> getRecipeById(@PathVariable Long id) {
        return ResponseEntity
                .ok(recipeService.getRecipeById(id));
    }

    @GetMapping("/search")
    @PreAuthorize("permitAll()")
    @Operation(
            summary = "Recepten zoeken",
            description = "Zoekt recepten op basis van een opgegeven zoekterm."
    )
    public ResponseEntity<List<RecipeResponseDto>> searchRecipes(
            @Parameter(description = "De zoekterm voor het vinden van recepten", example = "Diner")
            @RequestParam String query
    ) {
        return ResponseEntity.ok(
                recipeSearchService.searchRecipes(query)
        );
    }

    @GetMapping("/form_data")
    @PreAuthorize("permitAll()")
    @Operation(
            summary = "Formuliergegevens ophalen",
            description = "Haalt de benodigde gegevens op om het receptenformulier in te vullen."
    )
    public ResponseEntity<RecipeFormResponseDto> getRecipeFormData() {
        return ResponseEntity.ok(
                recipeFormService.getRecipeFormData()
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    @Operation(
            summary = "Nieuw recept toevoegen",
            description = "Maakt een nieuw recept aan. Alleen gebruikers met de rol CHEF of ADMIN hebben toegang."
    )
    public ResponseEntity<RecipeResponseDto> createRecipe(
            @Valid @RequestBody RecipeFormRequestDto requestDto
    ) {

        RecipeResponseDto newRecipe =
                recipeService.createRecipe(requestDto);

        URI location = locationUriBuilder.buildLocation(
                newRecipe.recipeId()
        );

        return ResponseEntity
                .created(location)
                .body(newRecipe);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    @Operation(
            summary = "Recept wijzigen",
            description = "Wijzigt een bestaand recept op basis van het opgegeven ID. Alleen gebruikers met de rol CHEF of ADMIN hebben toegang."
    )
    public ResponseEntity<RecipeResponseDto> updateRecipe(
            @PathVariable Long id,
            @Valid @RequestBody RecipeFormRequestDto requestDto
    ) {

        RecipeResponseDto updatedRecipe =
                recipeService.updateRecipe(id, requestDto);

        return ResponseEntity.ok(updatedRecipe);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    @Operation(
            summary = "Recept verwijderen",
            description = "Verwijdert een bestaand recept op basis van het opgegeven ID."
    )
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id){

        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
