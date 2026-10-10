package nl.novi.recipeapp.services;

import nl.novi.recipeapp.entities.IngredientEntity;
import nl.novi.recipeapp.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<IngredientEntity> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public IngredientEntity getIngredientById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Ingrediënt niet gevonden met ID: " + id
                        )
                );
    }

    public IngredientEntity createIngredient(IngredientEntity ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public IngredientEntity updateIngredient(
            Long id,
            IngredientEntity ingredient) {

        IngredientEntity existingIngredient = getIngredientById(id);

        existingIngredient.setName(ingredient.getName());

        return ingredientRepository.save(existingIngredient);
    }



    public void deleteIngredient(Long id) {
        IngredientEntity existingIngredient = getIngredientById(id);

        ingredientRepository.delete(existingIngredient);
    }

}
