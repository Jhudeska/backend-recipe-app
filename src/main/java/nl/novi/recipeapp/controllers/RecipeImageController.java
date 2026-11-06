package nl.novi.recipeapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import nl.novi.recipeapp.dtos.recipeimage.RecipeImageRequestDto;
import nl.novi.recipeapp.dtos.recipeimage.RecipeImageResponseDto;
import nl.novi.recipeapp.entities.RecipeImageEntity;
import nl.novi.recipeapp.services.RecipeImageService;
import nl.novi.recipeapp.services.RecipeImageStorageService;
import nl.novi.recipeapp.utils.LocationUriBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/recipe_images")
@Tag(name = "Receptafbeeldingen", description = "Api voor het beheren van afbeeldingen bij recepten")
public class RecipeImageController {

    private final RecipeImageService recipeImageService;
    private final RecipeImageStorageService storageService;
    private final LocationUriBuilder locationUriBuilder;

//    private final Path uploadDirectory =
//            Path.of("uploads", "recipes").toAbsolutePath().normalize();

    public RecipeImageController(
            RecipeImageService recipeImageService, RecipeImageStorageService storageService, LocationUriBuilder locationUriBuilder) {
        this.recipeImageService = recipeImageService;
        this.storageService = storageService;
        this.locationUriBuilder = locationUriBuilder;
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    @Operation(
            summary = "Alle receptafbeeldingen ophalen",
            description = "Haalt alle beschikbare receptafbeeldingen uit de database op."
    )
    public ResponseEntity<List<RecipeImageResponseDto>> getAllRecipeImages() {
        return ResponseEntity.ok(
                recipeImageService.getAllRecipeImages()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    @Operation(
            summary = "Receptafbeelding ophalen",
            description = "Haalt een specifieke receptafbeelding op aan de hand van het opgegeven ID.")
    public ResponseEntity<RecipeImageResponseDto> getRecipeImageById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                recipeImageService.getRecipeImageById(id)
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    @Operation(
            summary = "Receptafbeelding toevoegen",
            description = "Voegt een afbeelding toe aan een recept op basis van de opgegeven afbeeldingsgegevens. Alleen CHEF en ADMIN hebben toegang."
    )
    public ResponseEntity<RecipeImageResponseDto> createRecipeImage(
            @Valid @RequestBody RecipeImageRequestDto requestDto) {

        RecipeImageResponseDto createdRecipeImage =
                recipeImageService.createRecipeImage(requestDto);

        URI location = locationUriBuilder.buildLocation(
                createdRecipeImage.recipeId()
        );

        return ResponseEntity
                .created(location)
                .body(createdRecipeImage);

    }

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    @Operation(
            summary = "Receptafbeelding uploaden",
            description = "Uploadt een afbeeldingsbestand en koppelt dit aan een recept. Alleen CHEF en ADMIN hebben toegang."
    )
    public ResponseEntity<RecipeImageResponseDto> uploadRecipeImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("recipeId") Long recipeId) throws IOException, IOException {

//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().build();
//        }

//        if (file.getContentType() == null ||
//                !file.getContentType().startsWith("image/")) {
//            return ResponseEntity.badRequest().build();
//        }

//        Files.createDirectories(uploadDirectory);
//
//        String originalFilename = file.getOriginalFilename();
//        String extension = "";
//
//        if (originalFilename != null &&
//                originalFilename.lastIndexOf('.') >= 0) {
//            extension = originalFilename.substring(
//                    originalFilename.lastIndexOf('.')
//            ).toLowerCase();
//        }
//
//        if (!List.of(".jpg", ".jpeg", ".png", ".gif", ".webp")
//                .contains(extension)) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        String filename = UUID.randomUUID() + extension;
//        Path destination = uploadDirectory.resolve(filename).normalize();
//
//        if (!destination.startsWith(uploadDirectory)) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        file.transferTo(destination);

        // 1. Afbeelding fysiek opslaan
        String imageUrl = storageService.store(file);

        try {
            // 2. Afbeelding koppelen aan recept
            RecipeImageRequestDto requestDto =
                    new RecipeImageRequestDto(imageUrl, recipeId);

            // 3. Afbeeldingsgegevens opslaan in database
            RecipeImageResponseDto response =
                    recipeImageService.createRecipeImage(requestDto);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);

        } catch (RuntimeException exception) {

            // 4. Bestand opruimen als database-opslag mislukt
            storageService.deleteIfLocal(imageUrl);

            throw exception;
        }
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    @Operation(
            summary = "Receptafbeelding wijzigen",
            description = "Wijzigt de gegevens van een bestaande receptafbeelding. Alleen CHEF en ADMIN hebben toegang."
    )
    public ResponseEntity<RecipeImageResponseDto> updateRecipeImage(
            @PathVariable Long id,
            @Valid @RequestBody RecipeImageRequestDto requestDto) {

        return ResponseEntity.ok(
                recipeImageService.updateRecipeImage(id, requestDto)
        );
    }

//    @PutMapping(
//            value = "/{id}/upload",
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
//    )
//    public ResponseEntity<RecipeImageResponseDto> updateRecipeImageUpload(
//            @PathVariable Long id,
//            @RequestParam("file") MultipartFile file) throws IOException {
//
//        String imageUrl = storageService.store(file);
//
//        try {
//            RecipeImageRequestDto requestDto =
//                    new RecipeImageRequestDto(
//                            imageUrl,
//                            recipeImageService.getRecipeImageById(id).getRecipeId()
//                    );
//
//            RecipeImageResponseDto response =
//                    recipeImageService.updateRecipeImage(id, requestDto);
//
//            return ResponseEntity.ok(response);
//
//        } catch (RuntimeException exception) {
//            storageService.deleteIfLocal(imageUrl);
//            throw exception;
//        }
//    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    @Operation(
            summary = "Receptafbeelding verwijderen",
            description = "Verwijdert een bestaande receptafbeelding. Alleen CHEF en ADMIN hebben toegang."
    )
    public ResponseEntity<Void> deleteRecipeImage(
            @PathVariable Long id) {

        recipeImageService.deleteRecipeImage(id);

        return ResponseEntity.noContent().build();
    }
}