package nl.novi.recipeapp.repositories;

import nl.novi.recipeapp.entities.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {
}
