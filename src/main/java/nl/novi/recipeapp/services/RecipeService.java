package nl.novi.recipeapp.services;

import jakarta.transaction.Transactional;
import nl.novi.recipeapp.dtos.recipe.RecipeFormRequestDto;
import nl.novi.recipeapp.dtos.recipe.RecipeRequestDto;
import nl.novi.recipeapp.dtos.recipe.RecipeResponseDto;
import nl.novi.recipeapp.dtos.recipeingredient.RecipeIngredientFormDto;
import nl.novi.recipeapp.dtos.recipeingredient.RecipeIngredientRequestDto;
import nl.novi.recipeapp.dtos.recipe.RecipeDetailsRequestDto;
import nl.novi.recipeapp.entities.CategoryEntity;
import nl.novi.recipeapp.entities.IngredientEntity;
import nl.novi.recipeapp.entities.RecipeEntity;
import nl.novi.recipeapp.entities.RecipeIngredientEntity;
import nl.novi.recipeapp.mappers.RecipeMapper;
import nl.novi.recipeapp.repositories.*;
import org.springframework.stereotype.Service;
import nl.novi.recipeapp.exceptions.ResourceNotFoundException;

import java.util.List;

@Service
@Transactional
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final RecipeImageRepository recipeImageRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeMapper recipeMapper;

    public RecipeService(RecipeRepository recipeRepository, CategoryRepository categoryRepository, RecipeImageRepository recipeImageRepository, RecipeIngredientRepository recipeIngredientRepository, IngredientRepository ingredientRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.recipeImageRepository = recipeImageRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeMapper = recipeMapper;
    }

    public List<RecipeResponseDto> getAllRecipes() {
        return recipeRepository.findAll()
                .stream()
                .map(recipeMapper::toResponseDto)
                .toList();
    }

    public RecipeResponseDto getRecipeById(Long id) {
       RecipeEntity recipe = findRecipeById(id);
                return recipeMapper.toResponseDto(recipe);
    }

    public RecipeResponseDto createRecipe(RecipeFormRequestDto requestDto) {

        // 1. Categorie ophalen
        CategoryEntity category = categoryRepository
                .findById(requestDto.categoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Categorie niet gevonden met ID: "
                                        + requestDto.categoryId()
                        )
                );

        // 2. Recept aanmaken vanuit de details DTO
        RecipeEntity recipe = recipeMapper.toEntity(requestDto.recipe());

        // 3. Categorie koppelen
        recipe.setCategory(category);

        // 4. Recept opslaan
        RecipeEntity savedRecipe = recipeRepository.save(recipe);

        // 5. Ingrediënten opslaan en koppelen
        saveRecipeIngredients(
                savedRecipe,
                requestDto.ingredients()
        );

        // 6. Response teruggeven
        return recipeMapper.toResponseDto(savedRecipe);
    }





    public RecipeResponseDto updateRecipe(
            Long id,
            RecipeFormRequestDto requestDto
    ) {

        // 1. Bestaand recept ophalen
        RecipeEntity existingRecipe = findRecipeById(id);

        // 2. Categorie ophalen
        CategoryEntity category = categoryRepository
                .findById(requestDto.categoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Categorie niet gevonden met ID: "
                                        + requestDto.categoryId()
                        )
                );

        // 3. Receptgegevens bijwerken
        RecipeDetailsRequestDto recipeDto = requestDto.recipe();

        existingRecipe.setTitle(recipeDto.title());
        existingRecipe.setDescription(recipeDto.description());
        existingRecipe.setInstructions(recipeDto.instructions());
        existingRecipe.setPreparationTime(recipeDto.preparationTime());
        existingRecipe.setServings(recipeDto.servings());
        existingRecipe.setCategory(category);

        // 4. Recept opslaan
        RecipeEntity updatedRecipe = recipeRepository.save(existingRecipe);

        // 5. Oude ingrediënten verwijderen
        recipeIngredientRepository.deleteByRecipe_RecipeId(id);

        // 6. Nieuwe ingrediënten koppelen
        saveRecipeIngredients(
                updatedRecipe,
                requestDto.ingredients()
        );

        // 7. Volledige response teruggeven
        return recipeMapper.toResponseDto(updatedRecipe);
    }




    public void deleteRecipe(Long id) {

        RecipeEntity existingRecipe = findRecipeById(id);

        // Verwijder eerst de afbeelding.
        recipeImageRepository
                .findByRecipe_RecipeId(id)
                .ifPresent(image -> {
                    existingRecipe.setRecipeImage(null);
                    recipeImageRepository.delete(image);
                });;

        // Verwijder daarna de koppelingen met ingrediënten.
        recipeIngredientRepository
                .deleteByRecipe_RecipeId(id);

        // Verwijder ten slotte het recept.
        recipeRepository.delete(existingRecipe);
    }

    private RecipeEntity findRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Recept niet gevonden met ID: " + id
                        )
                );
    }

    private void saveRecipeIngredients(
            RecipeEntity recipe,
            List<RecipeIngredientFormDto> ingredients
    ) {
        if (ingredients == null || ingredients.isEmpty()) {
            return;
        }

        for (RecipeIngredientFormDto ingredientDto : ingredients) {
            IngredientEntity ingredient = ingredientRepository
                    .findById(ingredientDto.ingredientId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Ingrediënt niet gevonden met ID: "
                                            + ingredientDto.ingredientId()
                            )
                    );

            RecipeIngredientEntity recipeIngredient =
                    new RecipeIngredientEntity();

            recipeIngredient.setRecipe(recipe);
            recipeIngredient.setIngredient(ingredient);
            recipeIngredient.setQuantity(ingredientDto.quantity());
            recipeIngredient.setUnit(ingredientDto.unit());

            recipeIngredientRepository.save(recipeIngredient);
        }
    }
}
