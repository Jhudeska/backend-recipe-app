package nl.novi.recipeapp.services;

import nl.novi.recipeapp.entities.RecipeImageEntity;
import nl.novi.recipeapp.repositories.RecipeImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeImageService {
    
    private final RecipeImageRepository recipeImageRepository;


    public RecipeImageService(
            RecipeImageRepository recipeImageRepository) {
        this.recipeImageRepository = recipeImageRepository;
    }

    public List<RecipeImageEntity> getAllRecipeImages() {
        return recipeImageRepository.findAll();
    }

    public RecipeImageEntity getRecipeImageById(Long id) {
        return recipeImageRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "RecipeImage niet gevonden met id: " + id
                        )
                );
    }

    public RecipeImageEntity createRecipeImage(
            RecipeImageEntity recipeImage) {
        return recipeImageRepository.save(recipeImage);
    }

    public RecipeImageEntity updateRecipeImage(
            Long id,
            RecipeImageEntity updatedRecipeImage) {

        RecipeImageEntity existingRecipeImage =
                getRecipeImageById(id);

        existingRecipeImage.setImageUrl(
                updatedRecipeImage.getImageUrl()
        );

        return recipeImageRepository.save(existingRecipeImage);
    }

    public void deleteRecipeImage(Long id) {
        RecipeImageEntity recipeImage = getRecipeImageById(id);
        recipeImageRepository.delete(recipeImage);
    }
}
