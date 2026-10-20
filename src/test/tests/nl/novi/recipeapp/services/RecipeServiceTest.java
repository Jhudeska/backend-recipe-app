
package nl.novi.recipeapp.services;

import nl.novi.recipeapp.dtos.recipe.RecipeRequestDto;
import nl.novi.recipeapp.dtos.recipe.RecipeResponseDto;
import nl.novi.recipeapp.entities.CategoryEntity;
import nl.novi.recipeapp.entities.RecipeEntity;
import nl.novi.recipeapp.entities.RecipeImageEntity;
import nl.novi.recipeapp.mappers.RecipeMapper;
import nl.novi.recipeapp.repositories.CategoryRepository;
import nl.novi.recipeapp.repositories.RecipeImageRepository;
import nl.novi.recipeapp.repositories.RecipeIngredientRepository;
import nl.novi.recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private RecipeImageRepository recipeImageRepository;

    @Mock
    private RecipeIngredientRepository recipeIngredientRepository;

    @Mock
    private RecipeMapper recipeMapper;

    @InjectMocks
    private RecipeService recipeService;

    private RecipeEntity recipe;
    private CategoryEntity category;
    private RecipeRequestDto requestDto;
    private RecipeResponseDto responseDto;

    @BeforeEach
    void setUp() {
        recipe = mock(RecipeEntity.class);
        category = mock(CategoryEntity.class);
        requestDto = mock(RecipeRequestDto.class);
        responseDto = mock(RecipeResponseDto.class);
    }

    // GET ALL RECIPES

    @Test
    void getAllRecipesReturnsMappedRecipes() {
        RecipeEntity secondRecipe = mock(RecipeEntity.class);
        RecipeResponseDto secondResponse = mock(RecipeResponseDto.class);

        when(recipeRepository.findAll())
                .thenReturn(List.of(recipe, secondRecipe));
        when(recipeMapper.toResponseDto(recipe)).thenReturn(responseDto);
        when(recipeMapper.toResponseDto(secondRecipe))
                .thenReturn(secondResponse);

        List<RecipeResponseDto> result = recipeService.getAllRecipes();

        assertEquals(2, result.size());
        assertSame(responseDto, result.get(0));
        assertSame(secondResponse, result.get(1));

        verify(recipeRepository).findAll();
        verify(recipeMapper).toResponseDto(recipe);
        verify(recipeMapper).toResponseDto(secondRecipe);
    }

    @Test
    void getAllRecipesReturnsEmptyListWhenNoRecipesExist() {
        when(recipeRepository.findAll()).thenReturn(List.of());

        List<RecipeResponseDto> result = recipeService.getAllRecipes();

        assertTrue(result.isEmpty());

        verify(recipeRepository).findAll();
        verifyNoInteractions(recipeMapper);
    }

    // GET RECIPE BY ID

    @Test
    void getRecipeByIdReturnsMappedRecipe() {
        when(recipeRepository.findById(1L))
                .thenReturn(Optional.of(recipe));
        when(recipeMapper.toResponseDto(recipe))
                .thenReturn(responseDto);

        RecipeResponseDto result = recipeService.getRecipeById(1L);

        assertSame(responseDto, result);

        verify(recipeRepository).findById(1L);
        verify(recipeMapper).toResponseDto(recipe);
    }

    @Test
    void getRecipeByIdThrowsExceptionWhenRecipeDoesNotExist() {
        when(recipeRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> recipeService.getRecipeById(99L)
        );

        assertEquals(
                "Recept niet gevonden met ID: 99",
                exception.getMessage()
        );

        verify(recipeRepository).findById(99L);
        verifyNoInteractions(recipeMapper);
    }

    // CREATE RECIPE

    @Test
    void createRecipeSavesRecipeWithCategory() {
        when(requestDto.categoryId()).thenReturn(1L);

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));
        when(recipeMapper.toEntity(requestDto)).thenReturn(recipe);
        when(recipeRepository.save(recipe)).thenReturn(recipe);
        when(recipeMapper.toResponseDto(recipe))
                .thenReturn(responseDto);

        RecipeResponseDto result = recipeService.createRecipe(requestDto);

        assertSame(responseDto, result);

        verify(categoryRepository).findById(1L);
        verify(recipeMapper).toEntity(requestDto);
        verify(recipe).setCategory(category);
        verify(recipeRepository).save(recipe);
        verify(recipeMapper).toResponseDto(recipe);
    }

    @Test
    void createRecipeThrowsExceptionWhenCategoryDoesNotExist() {
        when(requestDto.categoryId()).thenReturn(1L);
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> recipeService.createRecipe(requestDto)
        );

        assertEquals(
                "Categorie niet gevonden met ID: 1",
                exception.getMessage()
        );

        verify(categoryRepository).findById(1L);
        verifyNoInteractions(recipeMapper, recipeRepository);
    }

    // UPDATE RECIPE

    @Test
    void updateRecipeUpdatesAllFieldsAndReturnsResponse() {
        when(requestDto.categoryId()).thenReturn(1L);
        when(requestDto.title()).thenReturn("Spaghetti Bolognese");
        when(requestDto.description())
                .thenReturn("Een klassieke Italiaanse pasta.");
        when(requestDto.instructions())
                .thenReturn("Kook de pasta en maak de saus.");
        when(requestDto.preparationTime()).thenReturn(30);
        when(requestDto.servings()).thenReturn(4);

        when(recipeRepository.findById(1L))
                .thenReturn(Optional.of(recipe));
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));
        when(recipeRepository.save(recipe)).thenReturn(recipe);
        when(recipeMapper.toResponseDto(recipe))
                .thenReturn(responseDto);

        RecipeResponseDto result =
                recipeService.updateRecipe(1L, requestDto);

        assertSame(responseDto, result);

        verify(recipe).setTitle("Spaghetti Bolognese");
        verify(recipe).setDescription("Een klassieke Italiaanse pasta.");
        verify(recipe).setInstructions("Kook de pasta en maak de saus.");
        verify(recipe).setPreparationTime(30);
        verify(recipe).setServings(4);
        verify(recipe).setCategory(category);

        verify(recipeRepository).save(recipe);
        verify(recipeMapper).toResponseDto(recipe);
    }

    @Test
    void updateRecipeThrowsExceptionWhenRecipeDoesNotExist() {
        when(recipeRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> recipeService.updateRecipe(99L, requestDto)
        );

        assertEquals(
                "Recept niet gevonden met ID: 99",
                exception.getMessage()
        );

        verify(recipeRepository).findById(99L);
        verifyNoInteractions(categoryRepository, recipeMapper);
    }

    @Test
    void updateRecipeThrowsExceptionWhenCategoryDoesNotExist() {
        when(recipeRepository.findById(1L))
                .thenReturn(Optional.of(recipe));
        when(requestDto.categoryId()).thenReturn(1L);
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> recipeService.updateRecipe(1L, requestDto)
        );

        assertEquals(
                "Categorie niet gevonden met ID: 1",
                exception.getMessage()
        );

        verify(recipeRepository, never()).save(any());
        verify(recipeMapper, never()).toResponseDto(any());
    }

    // DELETE RECIPE

    @Test
    void deleteRecipeDeletesImageIngredientsAndRecipe() {
        RecipeImageEntity image = mock(RecipeImageEntity.class);

        when(recipeRepository.findById(1L))
                .thenReturn(Optional.of(recipe));
        when(recipeImageRepository.findByRecipe_RecipeId(1L))
                .thenReturn(Optional.of(image));

        recipeService.deleteRecipe(1L);

        verify(recipeImageRepository).findByRecipe_RecipeId(1L);
        verify(recipe).setRecipeImage(null);
        verify(recipeImageRepository).delete(image);
        verify(recipeIngredientRepository).deleteByRecipe_RecipeId(1L);
        verify(recipeRepository).delete(recipe);
    }

    @Test
    void deleteRecipeDeletesIngredientsAndRecipeWhenNoImageExists() {
        when(recipeRepository.findById(1L))
                .thenReturn(Optional.of(recipe));
        when(recipeImageRepository.findByRecipe_RecipeId(1L))
                .thenReturn(Optional.empty());

        recipeService.deleteRecipe(1L);

        verify(recipeImageRepository).findByRecipe_RecipeId(1L);
        verify(recipeImageRepository, never()).delete(any());
        verify(recipe, never()).setRecipeImage(any());

        verify(recipeIngredientRepository).deleteByRecipe_RecipeId(1L);
        verify(recipeRepository).delete(recipe);
    }

    @Test
    void deleteRecipeThrowsExceptionWhenRecipeDoesNotExist() {
        when(recipeRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> recipeService.deleteRecipe(99L)
        );

        assertEquals(
                "Recept niet gevonden met ID: 99",
                exception.getMessage()
        );

        verify(recipeRepository, never()).delete(any());
        verifyNoInteractions(
                recipeImageRepository,
                recipeIngredientRepository
        );
    }
}