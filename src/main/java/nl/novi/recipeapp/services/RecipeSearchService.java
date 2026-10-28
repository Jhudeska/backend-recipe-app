package nl.novi.recipeapp.services;

import nl.novi.recipeapp.dtos.recipe.RecipeResponseDto;
import nl.novi.recipeapp.mappers.RecipeMapper;
import nl.novi.recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RecipeSearchService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    public RecipeSearchService(
            RecipeRepository recipeRepository,
            RecipeMapper recipeMapper
    ) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }

    public List<RecipeResponseDto> searchRecipes(String query) {

        if (query == null || query.isBlank()) {
            return List.of();
        }

        String normalizedQuery = query.trim();

        return recipeRepository.searchRecipes(normalizedQuery)
                .stream()
                .map(recipeMapper::toResponseDto)
                .toList();
    }
}
