package nl.novi.recipeapp.services;

import jakarta.validation.Valid;
import nl.novi.recipeapp.dtos.recipeingredient.RecipeIngredientRequestDto;
import nl.novi.recipeapp.dtos.recipeingredient.RecipeIngredientResponseDto;
import nl.novi.recipeapp.entities.IngredientEntity;
import nl.novi.recipeapp.entities.RecipeEntity;
import nl.novi.recipeapp.entities.RecipeIngredientEntity;
import nl.novi.recipeapp.mappers.RecipeIngredientMapper;
import nl.novi.recipeapp.repositories.IngredientRepository;
import nl.novi.recipeapp.repositories.RecipeIngredientRepository;
import nl.novi.recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientMapper recipeIngredientMapper;

    public RecipeIngredientService(
            RecipeIngredientRepository recipeIngredientRepository,
            RecipeRepository recipeRepository,
            IngredientRepository ingredientRepository, RecipeIngredientMapper recipeIngredientMapper) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeIngredientMapper = recipeIngredientMapper;
    }

    @Transactional(readOnly = true)
    public List<RecipeIngredientResponseDto> getAllRecipeIngredients() {
        return recipeIngredientRepository.findAll()
                .stream()
                .map(recipeIngredientMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public RecipeIngredientResponseDto getRecipeIngredientById(Long id) {
        return recipeIngredientMapper.toResponseDto(
                findRecipeIngredientById(id)
        );
    }

    @Transactional(readOnly = true)
    public List<RecipeIngredientResponseDto> getRecipeIngredientsByRecipeId(Long recipeId) {
        return recipeIngredientRepository.findByRecipe_RecipeId(recipeId)
                .stream()
                .map(recipeIngredientMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RecipeIngredientResponseDto> getRecipeIngredientsByIngredientId(Long ingredientId) {
        return recipeIngredientRepository
                .findByIngredient_IngredientId(ingredientId)
                .stream()
                .map(recipeIngredientMapper::toResponseDto)
                .toList();
    }



    @Transactional
    public RecipeIngredientResponseDto createRecipeIngredient(
            RecipeIngredientRequestDto requestDto) {

        Long recipeId = requestDto.recipeId();
        Long ingredientId = requestDto.ingredientId();

        RecipeEntity recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Recipe not found with ID: " + recipeId
                ));

        IngredientEntity ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Ingredient not found with ID: " + ingredientId
                ));

        if (recipeIngredientRepository
                .existsByRecipe_RecipeIdAndIngredient_IngredientId(
                        recipeId, ingredientId)) {
            throw new IllegalArgumentException(
                    "This ingredient is already linked to recipe ID: "
                            + recipeId
            );
        }

        RecipeIngredientEntity recipeIngredient =
                new RecipeIngredientEntity();

        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setQuantity(requestDto.quantity());
        recipeIngredient.setUnit(normalizeUnit(requestDto.unit()));

        RecipeIngredientEntity savedRecipeIngredient =
                recipeIngredientRepository.save(recipeIngredient);

        return recipeIngredientMapper.toResponseDto(savedRecipeIngredient);
    }



    public RecipeIngredientResponseDto updateRecipeIngredient(Long id, @Valid RecipeIngredientRequestDto requestDto) {
        RecipeIngredientEntity existingRecipeIngredient =
                findRecipeIngredientById(id);

        Long recipeId = requestDto.recipeId();
        Long ingredientId = requestDto.ingredientId();

        boolean linkChanged =
                !existingRecipeIngredient.getRecipe().getRecipeId()
                        .equals(recipeId)
                        || !existingRecipeIngredient.getIngredient()
                        .getIngredientId().equals(ingredientId);

        if (linkChanged
                && recipeIngredientRepository
                .existsByRecipe_RecipeIdAndIngredient_IngredientIdAndRecipeIngredientIdNot(
                        recipeId, ingredientId, id)) {
            throw new IllegalArgumentException(
                    "This ingredient is already linked to recipe ID: "
                            + recipeId
            );
        }

        RecipeEntity recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException(
                        "Recipe not found with ID: " + recipeId
                ));

        IngredientEntity ingredient =
                ingredientRepository.findById(ingredientId)
                        .orElseThrow(() -> new RuntimeException(
                                "Ingredient not found with ID: " + ingredientId
                        ));

        existingRecipeIngredient.setRecipe(recipe);
        existingRecipeIngredient.setIngredient(ingredient);
        existingRecipeIngredient.setQuantity(requestDto.quantity());
        existingRecipeIngredient.setUnit(normalizeUnit(requestDto.unit()));

        RecipeIngredientEntity savedRecipeIngredient =
                recipeIngredientRepository.save(existingRecipeIngredient);

        return recipeIngredientMapper.toResponseDto(savedRecipeIngredient);

    }

    public void deleteRecipeIngredient(Long id) {
        RecipeIngredientEntity existingRecipeIngredient =
                findRecipeIngredientById(id);

        recipeIngredientRepository.delete(existingRecipeIngredient);
    }

    private RecipeIngredientEntity findRecipeIngredientById(Long id) {

        return recipeIngredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Recipe ingredient not found with ID: " + id
                ));
    }

    private String normalizeUnit(String unit) {
        if (unit == null || unit.isBlank()) {
            return null;
        }

        return unit.trim();
    }
}