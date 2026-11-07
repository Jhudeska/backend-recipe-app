package nl.novi.recipeapp.controllers;

import nl.novi.recipeapp.dtos.recipe.RecipeFormRequestDto;
import nl.novi.recipeapp.dtos.recipe.RecipeResponseDto;
import nl.novi.recipeapp.services.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RecipeService recipeService;

    // TEST 1: Alle recepten ophalen
    @Test
    void getAllRecipesReturnsOkAndRecipeList() throws Exception {

        // ARRANGE
        RecipeResponseDto recipe = new RecipeResponseDto(
                1L,
                "Spaghetti",
                "Italiaanse pasta",
                "Kook de pasta",
                30,
                4,
                1L,
                "Pasta",
                null,
                List.of(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(recipeService.getAllRecipes())
                .thenReturn(List.of(recipe));

        // ACT
        mockMvc.perform(get("/api/v1/recipes"))


        // ASSERT
                .andExpect(status().isOk())
                .andExpect(content()

                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Spaghetti"))
                .andExpect(jsonPath("$[0].recipeId").value(1));
    }

    // TEST 2: Eén recept ophalen op ID
    @Test
    void getRecipeByIdReturnsOkAndRecipe() throws Exception {

        // ARRANGE
        RecipeResponseDto recipe = new RecipeResponseDto(
                1L,
                "Spaghetti",
                "Italiaanse pasta",
                "Kook de pasta",
                30,
                4,
                1L,
                "Pasta",
                null,
                List.of(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(recipeService.getRecipeById(1L))
                .thenReturn(recipe);
        // ACT
        mockMvc.perform(get("/api/v1/recipes/1"))

        // ASSERT
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Spaghetti"))
                .andExpect(jsonPath("$.recipeId").value(1));
    }

    // TEST 3: Een nieuw recept aanmaken
    @Test
    void createRecipeReturnsCreatedAndRecipe() throws Exception {

        // ARRANGE
        RecipeResponseDto recipe = new RecipeResponseDto(
                1L,
                "Spaghetti",
                "Italiaanse pasta",
                "Kook de pasta",
                30,
                4,
                1L,
                "Pasta",
                null,
                List.of(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(recipeService.createRecipe(any(RecipeFormRequestDto.class)))
                .thenReturn(recipe);


        mockMvc.perform(post("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "Spaghetti",
                                  "description": "Italiaanse pasta",
                                  "instructions": "Kook de pasta",
                                  "preparationTime": 30,
                                  "servings": 4,
                                  "categoryId": 1
                                }
                                """))

                // ASSERT
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Spaghetti"))
                .andExpect(jsonPath("$.recipeId").value(1));
    }
}
