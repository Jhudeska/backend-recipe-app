package nl.novi.recipeapp.services;

import nl.novi.recipeapp.dtos.ingredient.IngredientRequestDto;
import nl.novi.recipeapp.dtos.ingredient.IngredientResponseDto;
import nl.novi.recipeapp.entities.IngredientEntity;
import nl.novi.recipeapp.mappers.IngredientMapper;
import nl.novi.recipeapp.repositories.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    public IngredientService(IngredientRepository ingredientRepository, IngredientMapper ingredientMapper) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
    }

    @Transactional
    public List<IngredientResponseDto> getAllIngredients() {
        return ingredientRepository.findAll()
                .stream()
                .map(ingredientMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public IngredientResponseDto getIngredientById(Long id) {

        IngredientEntity ingredient = findIngredientById(id);

        return ingredientMapper.toResponseDto(ingredient);
    }

    public IngredientResponseDto createIngredient(IngredientRequestDto requestDto) {

        if (ingredientRepository.existsByNameIgnoreCase(
                requestDto.name().trim())) {
            throw new IllegalArgumentException(
                    "Ingredient already exists: " + requestDto.name()
            );
        }

        IngredientRequestDto normalizedDto =
                new IngredientRequestDto(requestDto.name().trim());

        IngredientEntity ingredient =
                ingredientMapper.toEntity(normalizedDto);

        IngredientEntity savedIngredient = ingredientRepository.save(ingredient);

        return ingredientMapper.toResponseDto(savedIngredient);
    }

    public IngredientResponseDto updateIngredient(
            Long id,
            IngredientRequestDto requestDto) {

        IngredientEntity existingIngredient = findIngredientById(id);
        String newName = requestDto.name().trim();

        boolean nameChanged =
                !existingIngredient.getName().equalsIgnoreCase(newName);

        if (nameChanged
                && ingredientRepository.existsByNameIgnoreCase(newName)) {
            throw new IllegalArgumentException(
                    "Ingredient already exists: " + newName
            );
        }

        existingIngredient.setName(newName);

        IngredientEntity savedIngredient = ingredientRepository.save(existingIngredient);

        return ingredientMapper.toResponseDto(savedIngredient);
    }



    public void deleteIngredient(Long id) {
        IngredientEntity existingIngredient = findIngredientById(id);

        ingredientRepository.delete(existingIngredient);
    }

    private IngredientEntity findIngredientById(Long id) {

        return ingredientRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Ingredient not found with ID: " + id
                        )
                );
    }

}
