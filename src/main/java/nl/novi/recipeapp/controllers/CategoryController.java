package nl.novi.recipeapp.controllers;

import jakarta.validation.Valid;
import nl.novi.recipeapp.dtos.category.CategoryRequestDto;
import nl.novi.recipeapp.dtos.category.CategoryResponseDto;
import nl.novi.recipeapp.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        return ResponseEntity.ok(
                categoryService.getAllCategories()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                categoryService.getCategoryById(id)
        );
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(
            @Valid @RequestBody CategoryRequestDto categoryRequestDto) {

        CategoryResponseDto createdCategory =
                categoryService.createCategory(categoryRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryRequestDto categoryRequestDto) {

        return ResponseEntity.ok(
                categoryService.updateCategory(id, categoryRequestDto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long id) {

        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}
