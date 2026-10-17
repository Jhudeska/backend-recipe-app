package nl.novi.recipeapp.mappers;

import nl.novi.recipeapp.dtos.FavoriteDto;
import nl.novi.recipeapp.entities.FavoriteEntity;
import org.springframework.stereotype.Component;

@Component
public class FavoriteMapper {

    public FavoriteDto toDto(FavoriteEntity favorite) {

        return new FavoriteDto(
                favorite.getFavoriteId(),
                favorite.getRecipe().getRecipeId(),
                favorite.getRecipe().getTitle()
        );
    }
}
