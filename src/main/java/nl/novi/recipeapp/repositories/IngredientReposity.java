package nl.novi.recipeapp.repositories;

import nl.novi.recipeapp.entities.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientReposity extends JpaRepository<IngredientEntity, Long> {
}
