package nl.novi.recipeapp.controllers;

import nl.novi.recipeapp.entities.FavoriteEntity;
import nl.novi.recipeapp.services.FavoriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavoriteEntity>> getAllFavorites(
            @PathVariable Long userId) {
        return ResponseEntity
                .ok(favoriteService.getAllFavorites(userId));
    }

    @GetMapping("/user/{userId}/recipe/{recipeId}")
    public ResponseEntity<FavoriteEntity> getFavoriteByRecipeId(
            @PathVariable Long userId,
            @PathVariable Long recipeId) {
        return ResponseEntity
                .ok(favoriteService.getFavoriteByRecipeId(userId, recipeId));
    }

    @PostMapping("/user/{userId}/recipe/{recipeId}")
    public ResponseEntity<FavoriteEntity> createFavorite(
            @PathVariable Long userId,
            @PathVariable Long recipeId) {
        FavoriteEntity newFavorite =
                favoriteService.createFavorite(userId, recipeId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newFavorite);
    }

    @DeleteMapping("/user/{userId}/recipe/{recipeId}")
    public ResponseEntity<Void> deleteFavorite(
            @PathVariable Long userId,
            @PathVariable Long recipeId) {
        favoriteService.deleteFavorite(userId, recipeId);

        return ResponseEntity.noContent().build();
    }
}