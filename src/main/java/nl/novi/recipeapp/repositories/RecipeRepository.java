package nl.novi.recipeapp.repositories;

import nl.novi.recipeapp.entities.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {

    @Query("""
            SELECT DISTINCT r
            FROM RecipeEntity r
            LEFT JOIN r.category c
            LEFT JOIN r.ingredients ri
            LEFT JOIN ri.ingredient i
            WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :query, '%'))
               OR LOWER(r.description) LIKE LOWER(CONCAT('%', :query, '%'))
               OR LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%'))
               OR LOWER(i.name) LIKE LOWER(CONCAT('%', :query, '%'))
            """)
    List<RecipeEntity> searchRecipes(
            @Param("query") String query
    );
}
