package nl.novi.recipeapp.repositories;

import nl.novi.recipeapp.entities.RecipeImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeImageRepository  extends JpaRepository<RecipeImageEntity, Long> {
}
