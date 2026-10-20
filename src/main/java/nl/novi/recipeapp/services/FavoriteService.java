package nl.novi.recipeapp.services;

import org.springframework.transaction.annotation.Transactional;
import nl.novi.recipeapp.dtos.favorite.FavoriteRequestDto;
import nl.novi.recipeapp.dtos.favorite.FavoriteResponseDto;
import nl.novi.recipeapp.entities.FavoriteEntity;
import nl.novi.recipeapp.entities.RecipeEntity;
import nl.novi.recipeapp.entities.UserProfileEntity;
import nl.novi.recipeapp.mappers.FavoriteMapper;
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
    private final FavoriteMapper favoriteMapper;

    public FavoriteService(
            FavoriteRepository favoriteRepository,
            RecipeRepository recipeRepository,
            UserProfileRepository userProfileRepository, FavoriteMapper favoriteMapper) {
        this.favoriteRepository = favoriteRepository;
        this.recipeRepository = recipeRepository;
        this.userProfileRepository = userProfileRepository;
        this.favoriteMapper = favoriteMapper;
    }

    @Transactional(readOnly = true)
    public List<FavoriteResponseDto> getAllFavorites(Long userId) {
        return favoriteRepository.findByUserProfile_UserId(userId)
                .stream()
                .map(favoriteMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public FavoriteResponseDto getFavoriteByRecipeId(Long userId, Long recipeId) {

        FavoriteEntity favorite = findFavorite(userId, recipeId);

        return favoriteMapper.toResponseDto(favorite);
    }

    public FavoriteResponseDto createFavorite(Long userId, FavoriteRequestDto requestDto) {

        Long recipeId = requestDto.recipeId();

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

        FavoriteEntity savedFavorite = favoriteRepository.save(favorite);
        return favoriteMapper.toResponseDto(savedFavorite);
    }

    public void deleteFavorite(Long userId, Long recipeId) {
        FavoriteEntity existingFavorite =
                findFavorite(userId, recipeId);

        favoriteRepository.delete(existingFavorite);
    }

    private FavoriteEntity findFavorite(Long userId, Long recipeId) {

        return favoriteRepository
                .findByUserProfile_UserIdAndRecipe_RecipeId(
                        userId,
                        recipeId
                )
                .orElseThrow(() -> new RuntimeException(
                        "Favorite not found for user id: " + userId
                                + " and recipe id: " + recipeId
                ));
    }
}