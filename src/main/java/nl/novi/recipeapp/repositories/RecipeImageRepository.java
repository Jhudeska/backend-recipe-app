package nl.novi.recipeapp.repositories;

import nl.novi.recipeapp.entities.RecipeImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeImageRepository  extends JpaRepository<RecipeImageEntity, Long> {

    boolean existsByRecipe_RecipeId(Long recipeId);

    Optional<RecipeImageEntity> findByRecipe_RecipeId(Long recipeId);
}
