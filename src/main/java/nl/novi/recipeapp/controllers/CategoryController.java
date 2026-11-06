package nl.novi.recipeapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import nl.novi.recipeapp.dtos.category.CategoryRequestDto;
import nl.novi.recipeapp.dtos.category.CategoryResponseDto;
import nl.novi.recipeapp.services.CategoryService;
import nl.novi.recipeapp.utils.LocationUriBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Categorieën", description = "API voor het beheren van receptcategorieën")
public class CategoryController {

    private final CategoryService categoryService;
    private final LocationUriBuilder locationUriBuilder;

    public CategoryController(CategoryService categoryService, LocationUriBuilder locationUriBuilder) {
        this.categoryService = categoryService;
        this.locationUriBuilder = locationUriBuilder;
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    @Operation(
            summary = "Alle categorieën ophalen",
            description = "Haalt alle beschikbare receptcategorieën uit de database op."
    )
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        return ResponseEntity.ok(
                categoryService.getAllCategories()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    @Operation(
            summary = "Categorie ophalen",
            description = "Haalt een specifieke categorie op aan de hand van het opgegeven ID."
    )
    public ResponseEntity<CategoryResponseDto> getCategoryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                categoryService.getCategoryById(id)
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    @Operation(
            summary = "Nieuwe categorie toevoegen",
            description = "Voegt een nieuwe receptcategorie toe. Alleen gebruikers met de rol CHEF of ADMIN hebben toegang."
    )
    public ResponseEntity<CategoryResponseDto> createCategory(
            @Valid @RequestBody CategoryRequestDto categoryRequestDto) {

        CategoryResponseDto createdCategory =
                categoryService.createCategory(categoryRequestDto);

        URI location = locationUriBuilder.buildLocation(
                createdCategory.categoryId()
        );

        return ResponseEntity
                .created(location)
                .body(createdCategory);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    @Operation(
            summary = "Categorie wijzigen",
            description = "Wijzigt een bestaande categorie op basis van het opgegeven ID. Alleen gebruikers met de rol CHEF of ADMIN hebben toegang."
    )
    public ResponseEntity<CategoryResponseDto> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryRequestDto categoryRequestDto) {

        return ResponseEntity.ok(
                categoryService.updateCategory(id, categoryRequestDto)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    @Operation(
            summary = "Categorie verwijderen",
            description = "Verwijdert een bestaande categorie op basis van het opgegeven ID. Alleen gebruikers met de rol CHEF of ADMIN hebben toegang."
    )
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long id) {

        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}
