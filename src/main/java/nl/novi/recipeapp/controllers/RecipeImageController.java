package nl.novi.recipeapp.controllers;

import jakarta.validation.Valid;
import nl.novi.recipeapp.dtos.recipeimage.RecipeImageRequestDto;
import nl.novi.recipeapp.dtos.recipeimage.RecipeImageResponseDto;
import nl.novi.recipeapp.entities.RecipeImageEntity;
import nl.novi.recipeapp.services.RecipeImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/recipe_images")
public class RecipeImageController {

    private final RecipeImageService recipeImageService;

    private final Path uploadDirectory =
            Path.of("uploads", "recipes").toAbsolutePath().normalize();

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

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<RecipeImageResponseDto> uploadRecipeImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("recipeId") Long recipeId) throws IOException, IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (file.getContentType() == null ||
                !file.getContentType().startsWith("image/")) {
            return ResponseEntity.badRequest().build();
        }

        Files.createDirectories(uploadDirectory);

        String originalFilename = file.getOriginalFilename();
        String extension = "";

        if (originalFilename != null &&
                originalFilename.lastIndexOf('.') >= 0) {
            extension = originalFilename.substring(
                    originalFilename.lastIndexOf('.')
            ).toLowerCase();
        }

        if (!List.of(".jpg", ".jpeg", ".png", ".gif", ".webp")
                .contains(extension)) {
            return ResponseEntity.badRequest().build();
        }

        String filename = UUID.randomUUID() + extension;
        Path destination = uploadDirectory.resolve(filename).normalize();

        if (!destination.startsWith(uploadDirectory)) {
            return ResponseEntity.badRequest().build();
        }

        file.transferTo(destination);

        String imageUrl = "/uploads/recipes/" + filename;

        try {
            RecipeImageRequestDto requestDto =
                    new RecipeImageRequestDto(imageUrl, recipeId);

            RecipeImageResponseDto response =
                    recipeImageService.createRecipeImage(requestDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException exception) {
            Files.deleteIfExists(destination);
            throw exception;
        }
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