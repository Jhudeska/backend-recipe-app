package nl.novi.recipeapp.dtos.recipe;

import io.swagger.v3.oas.annotations.media.Schema;
import nl.novi.recipeapp.dtos.category.CategoryResponseDto;
import nl.novi.recipeapp.dtos.ingredient.IngredientFormResponseDto;

import java.util.List;

@Schema(description = "Gegevens voor het vullen van een receptformulier")
public record RecipeFormResponseDto(

        @Schema(
                description = "Lijst met beschikbare categorieën voor het receptformulier"
        )
        List<CategoryResponseDto> categories,


        @Schema(
                description = "Lijst met beschikbare ingrediënten voor het receptformulier"
        )
        List<IngredientFormResponseDto> ingredients

) {}
