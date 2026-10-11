package nl.novi.recipeapp.services;

import nl.novi.recipeapp.entities.FavoriteEntity;
import nl.novi.recipeapp.entities.RecipeEntity;
import nl.novi.recipeapp.entities.UserProfileEntity;
import nl.novi.recipeapp.repositories.FavoriteRepository;
import nl.novi.recipeapp.repositories.RecipeRepository;
import nl.novi.recipeapp.repositories.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final RecipeRepository recipeRepository;
    private final UserProfileRepository userProfileRepository;

    public FavoriteService(
            FavoriteRepository favoriteRepository,
            RecipeRepository recipeRepository,
            UserProfileRepository userProfileRepository) {
        this.favoriteRepository = favoriteRepository;
        this.recipeRepository = recipeRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public List<FavoriteEntity> getAllFavorites(Long userId) {
        return favoriteRepository.findByUserProfile_UserId(userId);
    }

    public FavoriteEntity getFavoriteByRecipeId(Long userId, Long recipeId) {
        return favoriteRepository
                .findByUserProfile_UserIdAndRecipe_RecipeId(userId, recipeId)
                .orElseThrow(() -> new RuntimeException(
                        "Favorite not found for user id: " + userId
                                + " and recipe id: " + recipeId));
    }

    public FavoriteEntity createFavorite(Long userId, Long recipeId) {
        if (favoriteRepository
                .existsByUserProfile_UserIdAndRecipe_RecipeId(userId, recipeId)) {
            throw new RuntimeException(
                    "Recipe is already a favorite for user id: " + userId);
        }

        UserProfileEntity userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(
                        "User not found with id: " + userId));

        RecipeEntity recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException(
                        "Recipe not found with id: " + recipeId));

        FavoriteEntity favorite = new FavoriteEntity();
        favorite.setUserProfile(userProfile);
        favorite.setRecipe(recipe);

        return favoriteRepository.save(favorite);
    }

    public void deleteFavorite(Long userId, Long recipeId) {
        FavoriteEntity existingFavorite =
                getFavoriteByRecipeId(userId, recipeId);

        favoriteRepository.delete(existingFavorite);
    }
}