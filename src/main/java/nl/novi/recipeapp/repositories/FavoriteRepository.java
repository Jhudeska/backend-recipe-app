package nl.novi.recipeapp.repositories;

import nl.novi.recipeapp.entities.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {

    List<FavoriteEntity> findByUserProfile_UserId(Long userId);

    Optional<FavoriteEntity> findByUserProfile_UserIdAndRecipe_RecipeId(
            Long userId, Long recipeId);

    boolean existsByUserProfile_UserIdAndRecipe_RecipeId(
            Long userId, Long recipeId);
}