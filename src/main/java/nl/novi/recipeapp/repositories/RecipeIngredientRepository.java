package nl.novi.recipeapp.repositories;

import nl.novi.recipeapp.entities.RecipeIngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredientEntity, Long> {
    List<RecipeIngredientEntity> findByRecipe_RecipeId(Long recipeId);

    List<RecipeIngredientEntity> findByIngredient_IngredientId(Long ingredientId);

    boolean existsByRecipe_RecipeIdAndIngredient_IngredientId(
            Long recipeId,
            Long ingredientId
    );

    boolean existsByRecipe_RecipeIdAndIngredient_IngredientIdAndRecipeIngredientIdNot(
            Long recipeId,
            Long ingredientId,
            Long recipeIngredientId
    );

    void deleteByRecipe_RecipeId(Long recipeId);
}
