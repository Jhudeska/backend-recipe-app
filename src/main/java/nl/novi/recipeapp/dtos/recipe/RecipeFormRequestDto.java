package nl.novi.recipeapp.dtos.recipe;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import nl.novi.recipeapp.dtos.recipeingredient.RecipeIngredientFormDto;

import java.util.List;

public record RecipeFormRequestDto(

        @Valid
        @NotNull
        RecipeDetailsRequestDto recipe,

        @NotNull
        @Positive
        Long categoryId,

        @Valid
        List<@NotNull RecipeIngredientFormDto> ingredients
) {}

