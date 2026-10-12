package nl.novi.recipeapp.repositories;

import nl.novi.recipeapp.entities.RecipeIngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredientEntity, Long> {
    List<RecipeIngredientEntity> findByRecipe_RecipeId(Long recipeId);

    List<RecipeIngredientEntity> findByIngredient_IngredientId(Long ingredientId);
}
