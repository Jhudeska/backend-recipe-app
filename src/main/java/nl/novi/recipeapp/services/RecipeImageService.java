package nl.novi.recipeapp.services;

import nl.novi.recipeapp.dtos.recipeimage.RecipeImageRequestDto;
import nl.novi.recipeapp.dtos.recipeimage.RecipeImageResponseDto;
import nl.novi.recipeapp.entities.RecipeEntity;
import nl.novi.recipeapp.entities.RecipeImageEntity;
import nl.novi.recipeapp.mappers.RecipeImageMapper;
import nl.novi.recipeapp.repositories.RecipeImageRepository;
import nl.novi.recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecipeImageService {
    
    private final RecipeImageRepository recipeImageRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeImageMapper recipeImageMapper;


    public RecipeImageService(
            RecipeImageRepository recipeImageRepository, RecipeRepository recipeRepository, RecipeImageMapper recipeImageMapper) {
        this.recipeImageRepository = recipeImageRepository;
        this.recipeRepository = recipeRepository;
        this.recipeImageMapper = recipeImageMapper;
    }

    @Transactional(readOnly = true)
    public List<RecipeImageResponseDto> getAllRecipeImages() {
        return recipeImageRepository.findAll()
                .stream()
                .map(recipeImageMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public RecipeImageResponseDto getRecipeImageById(Long id) {
        return recipeImageMapper.toResponseDto(findRecipeImageById(id));
    }


    @Transactional
    public RecipeImageResponseDto createRecipeImage(
            RecipeImageRequestDto requestDto) {

        RecipeEntity recipe = recipeRepository.findById(
                requestDto.recipeId()
        ).orElseThrow(() -> new RuntimeException(
                "Recipe not found with ID: " + requestDto.recipeId()
        ));

        RecipeImageEntity existingImage =
                recipeImageRepository
                        .findByRecipe_RecipeId(requestDto.recipeId())
                        .orElse(null);

        if (existingImage != null) {
            existingImage.setImageUrl(
                    requestDto.imageUrl().trim()
            );

            RecipeImageEntity savedImage =
                    recipeImageRepository.save(existingImage);

            return recipeImageMapper.toResponseDto(savedImage);
        }

        RecipeImageEntity recipeImage = new RecipeImageEntity(
                requestDto.imageUrl().trim(),
                recipe
        );

        RecipeImageEntity savedRecipeImage =
                recipeImageRepository.save(recipeImage);

        return recipeImageMapper.toResponseDto(savedRecipeImage);
    }


    public RecipeImageResponseDto updateRecipeImage(
            Long id,
            RecipeImageRequestDto requestDto) {

        RecipeImageEntity existingRecipeImage =
                findRecipeImageById(id);

        RecipeEntity recipe = recipeRepository.findById(
                requestDto.recipeId()
        ).orElseThrow(() -> new RuntimeException(
                "Recipe not found with ID: " + requestDto.recipeId()
        ));

        boolean recipeChanged = !existingRecipeImage.getRecipe()
                .getRecipeId().equals(requestDto.recipeId());

        if (recipeChanged && recipeImageRepository.existsByRecipe_RecipeId(
                requestDto.recipeId())) {
            throw new IllegalArgumentException(
                    "An image already exists for recipe ID: "
                            + requestDto.recipeId()
            );
        }

        existingRecipeImage.setImageUrl(
                requestDto.imageUrl().trim()
        );
        existingRecipeImage.setRecipe(recipe);

        RecipeImageEntity savedRecipeImage = recipeImageRepository.save(existingRecipeImage);

        return recipeImageMapper.toResponseDto(savedRecipeImage);
    }

    public void deleteRecipeImage(Long id) {
        RecipeImageEntity recipeImage = findRecipeImageById(id);
        recipeImageRepository.delete(recipeImage);
    }

    private RecipeImageEntity findRecipeImageById(Long id) {

        return recipeImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Recipe image not found with ID: " + id
                ));
    }
}
