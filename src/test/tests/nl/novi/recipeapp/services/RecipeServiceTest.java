
package nl.novi.recipeapp.services;

import nl.novi.recipeapp.dtos.recipe.RecipeFormRequestDto;
import nl.novi.recipeapp.dtos.recipe.RecipeDetailsRequestDto;
import nl.novi.recipeapp.dtos.recipe.RecipeResponseDto;
import nl.novi.recipeapp.entities.CategoryEntity;
import nl.novi.recipeapp.entities.RecipeEntity;
import nl.novi.recipeapp.entities.RecipeImageEntity;
import nl.novi.recipeapp.mappers.RecipeMapper;
import nl.novi.recipeapp.repositories.*;
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
    private IngredientRepository ingredientRepository;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private ReviewRatingRepository reviewRatingRepository;

    @Mock
    private RecipeMapper recipeMapper;

    @InjectMocks
    private RecipeService recipeService;

    private RecipeEntity recipe;
    private CategoryEntity category;
    private RecipeFormRequestDto requestDto;
    private RecipeDetailsRequestDto recipeDetailsDto;
    private RecipeResponseDto responseDto;

    @BeforeEach
    void setUp() {
        recipe = mock(RecipeEntity.class);
        category = mock(CategoryEntity.class);
        requestDto = mock(RecipeFormRequestDto.class);
        recipeDetailsDto = mock(RecipeDetailsRequestDto.class);
        responseDto = mock(RecipeResponseDto.class);
    }

    // GET ALL RECIPES

    @Test
    void getAllRecipesReturnsMappedRecipes() {

        // ARRANGE
        RecipeEntity secondRecipe = mock(RecipeEntity.class);
        RecipeResponseDto secondResponse = mock(RecipeResponseDto.class);

        when(recipeRepository.findAll())
                .thenReturn(List.of(recipe, secondRecipe));
        when(recipeMapper.toResponseDto(recipe)).thenReturn(responseDto);
        when(recipeMapper.toResponseDto(secondRecipe))
                .thenReturn(secondResponse);

        // ACT
        List<RecipeResponseDto> result = recipeService.getAllRecipes();

        // ASSERT
        assertEquals(2, result.size());
        assertSame(responseDto, result.get(0));
        assertSame(secondResponse, result.get(1));

        verify(recipeRepository).findAll();
        verify(recipeMapper).toResponseDto(recipe);
        verify(recipeMapper).toResponseDto(secondRecipe);
    }

    @Test
    void getAllRecipesReturnsEmptyListWhenNoRecipesExist() {

        // ARRANGE
        when(recipeRepository.findAll()).thenReturn(List.of());

        // ACT
        List<RecipeResponseDto> result = recipeService.getAllRecipes();

        // ASSERT
        assertTrue(result.isEmpty());

        verify(recipeRepository).findAll();
        verifyNoInteractions(recipeMapper);
    }

    // GET RECIPE BY ID

    @Test
    void getRecipeByIdReturnsMappedRecipe() {

        // ARRANGE
        when(recipeRepository.findById(1L))
                .thenReturn(Optional.of(recipe));
        when(recipeMapper.toResponseDto(recipe))
                .thenReturn(responseDto);

        // ACT
        RecipeResponseDto result = recipeService.getRecipeById(1L);

        // ASSERT
        assertSame(responseDto, result);

        verify(recipeRepository).findById(1L);
        verify(recipeMapper).toResponseDto(recipe);
    }

    @Test
    void getRecipeByIdThrowsExceptionWhenRecipeDoesNotExist() {

        // ARRANGE
        when(recipeRepository.findById(99L))
                .thenReturn(Optional.empty());

        // ACT
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> recipeService.getRecipeById(99L)
        );

        // ASSERT
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

        // ARRANGE
        when(requestDto.categoryId()).thenReturn(1L);

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));
        when(requestDto.recipe()).thenReturn(recipeDetailsDto);
        when(recipeMapper.toEntity(recipeDetailsDto)).thenReturn(recipe);
        when(recipeRepository.save(recipe)).thenReturn(recipe);
        when(recipeMapper.toResponseDto(recipe))
                .thenReturn(responseDto);

        // ACT
        RecipeResponseDto result = recipeService.createRecipe(requestDto);

        // ASSERT
        assertSame(responseDto, result);

        verify(categoryRepository).findById(1L);
        verify(recipeMapper).toEntity(recipeDetailsDto);
        verify(recipe).setCategory(category);
        verify(recipeRepository).save(recipe);
        verify(recipeMapper).toResponseDto(recipe);
    }

    @Test
    void createRecipeThrowsExceptionWhenCategoryDoesNotExist() {

        // ARRANGE
        when(requestDto.categoryId()).thenReturn(1L);
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.empty());

        // ACT
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> recipeService.createRecipe(requestDto)
        );

        // ASSERT
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

        // ARRANGE
        when(requestDto.categoryId()).thenReturn(1L);
        when(requestDto.recipe()).thenReturn(recipeDetailsDto);
        when(requestDto.ingredients()).thenReturn(List.of());

        when(recipeDetailsDto.title())
                .thenReturn("Spaghetti Bolognese");
        when(recipeDetailsDto.description())
                .thenReturn("Een klassieke Italiaanse pasta.");
        when(recipeDetailsDto.instructions())
                .thenReturn("Kook de pasta en maak de saus.");
        when(recipeDetailsDto.preparationTime()).thenReturn(30);
        when(recipeDetailsDto.servings()).thenReturn(4);

        when(recipeRepository.findById(1L))
                .thenReturn(Optional.of(recipe));

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        when(recipeRepository.save(recipe))
                .thenReturn(recipe);

        when(recipeMapper.toResponseDto(recipe))
                .thenReturn(responseDto);

        // ACT
        RecipeResponseDto result =
                recipeService.updateRecipe(1L, requestDto);

        // ASSERT
        assertSame(responseDto, result);

        verify(recipe).setTitle("Spaghetti Bolognese");
        verify(recipe).setDescription("Een klassieke Italiaanse pasta.");
        verify(recipe).setInstructions("Kook de pasta en maak de saus.");
        verify(recipe).setPreparationTime(30);
        verify(recipe).setServings(4);
        verify(recipe).setCategory(category);

        verify(recipeRepository).save(recipe);
        verify(recipeIngredientRepository)
                .deleteByRecipe_RecipeId(1L);
        verify(recipeMapper).toResponseDto(recipe);
    }


    @Test
    void updateRecipeThrowsExceptionWhenRecipeDoesNotExist() {

        // ARRANGE
        when(recipeRepository.findById(99L))
                .thenReturn(Optional.empty());

        // ACT
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> recipeService.updateRecipe(99L, requestDto)
        );

        // ASSERT
        assertEquals(
                "Recept niet gevonden met ID: 99",
                exception.getMessage()
        );

        verify(recipeRepository).findById(99L);
        verifyNoInteractions(categoryRepository, recipeMapper);
    }

    @Test
    void updateRecipeThrowsExceptionWhenCategoryDoesNotExist() {

        // ARRANGE
        when(recipeRepository.findById(1L))
                .thenReturn(Optional.of(recipe));
        when(requestDto.categoryId()).thenReturn(1L);
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.empty());

        // ACT
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> recipeService.updateRecipe(1L, requestDto)
        );

        // ASSERT
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

        // ARRANGE
        RecipeImageEntity image = mock(RecipeImageEntity.class);

        when(recipeRepository.findById(1L))
                .thenReturn(Optional.of(recipe));
        when(recipeImageRepository.findByRecipe_RecipeId(1L))
                .thenReturn(Optional.of(image));

        // ACT
        recipeService.deleteRecipe(1L);

        // ASSERT
        verify(recipeImageRepository).findByRecipe_RecipeId(1L);
        verify(recipe).setRecipeImage(null);
        verify(recipeImageRepository).delete(image);
        verify(recipeIngredientRepository).deleteByRecipe_RecipeId(1L);
        verify(recipeRepository).delete(recipe);
    }

    @Test
    void deleteRecipeDeletesIngredientsAndRecipeWhenNoImageExists() {

        // ARRANGE
        when(recipeRepository.findById(1L))
                .thenReturn(Optional.of(recipe));
        when(recipeImageRepository.findByRecipe_RecipeId(1L))
                .thenReturn(Optional.empty());

        // ACT
        recipeService.deleteRecipe(1L);

        // ASSERT
        verify(recipeImageRepository).findByRecipe_RecipeId(1L);
        verify(recipeImageRepository, never()).delete(any());
        verify(recipe, never()).setRecipeImage(any());

        verify(recipeIngredientRepository).deleteByRecipe_RecipeId(1L);
        verify(recipeRepository).delete(recipe);
    }

    @Test
    void deleteRecipeThrowsExceptionWhenRecipeDoesNotExist() {

        // ARRANGE
        when(recipeRepository.findById(99L))
                .thenReturn(Optional.empty());
        // ACT
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> recipeService.deleteRecipe(99L)
        );

        // ASSERT
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