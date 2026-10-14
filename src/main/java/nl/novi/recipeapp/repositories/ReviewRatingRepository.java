package nl.novi.recipeapp.repositories;

import nl.novi.recipeapp.entities.ReviewRatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRatingRepository extends JpaRepository<ReviewRatingEntity, Long> {

}
