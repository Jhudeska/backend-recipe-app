package nl.novi.recipeapp.services;

import jakarta.transaction.Transactional;
import nl.novi.recipeapp.dtos.recipe.RecipeRequestDto;
import nl.novi.recipeapp.dtos.recipe.RecipeResponseDto;
import nl.novi.recipeapp.entities.CategoryEntity;
import nl.novi.recipeapp.entities.RecipeEntity;
import nl.novi.recipeapp.mappers.RecipeMapper;
import nl.novi.recipeapp.repositories.CategoryRepository;
import nl.novi.recipeapp.repositories.RecipeImageRepository;
import nl.novi.recipeapp.repositories.RecipeIngredientRepository;
import nl.novi.recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final RecipeImageRepository recipeImageRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeMapper recipeMapper;

    public RecipeService(RecipeRepository recipeRepository, CategoryRepository categoryRepository, RecipeImageRepository recipeImageRepository, RecipeIngredientRepository recipeIngredientRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.recipeImageRepository = recipeImageRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
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

    public RecipeResponseDto createRecipe(RecipeRequestDto recipeRequestDto) {
        CategoryEntity category = categoryRepository
                .findById(recipeRequestDto.categoryId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Categorie niet gevonden met ID: "
                                        + recipeRequestDto.categoryId()
                        )
                );

        RecipeEntity recipe = recipeMapper.toEntity(recipeRequestDto);

        // Hier leggen we de relatie met CategoryEntity.
        recipe.setCategory(category);


        RecipeEntity savedRecipe =  recipeRepository.save(recipe);
        return recipeMapper.toResponseDto(savedRecipe);
    }

    public RecipeResponseDto updateRecipe(Long id, RecipeRequestDto recipeRequestDto) {

        RecipeEntity existingRecipe = findRecipeById(id);

        CategoryEntity category = categoryRepository
                .findById(recipeRequestDto.categoryId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Categorie niet gevonden met ID: "
                                        + recipeRequestDto.categoryId()
                        )
                );

        existingRecipe.setTitle(recipeRequestDto.title());
        existingRecipe.setDescription(recipeRequestDto.description());
        existingRecipe.setInstructions(recipeRequestDto.instructions());
        existingRecipe.setPreparationTime(recipeRequestDto.preparationTime());
        existingRecipe.setServings(recipeRequestDto.servings());
        existingRecipe.setCategory(category);

        RecipeEntity updatedRecipe = recipeRepository.save(existingRecipe);

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
                        new RuntimeException(
                                "Recept niet gevonden met ID: " + id
                        )
                );
    }

}
