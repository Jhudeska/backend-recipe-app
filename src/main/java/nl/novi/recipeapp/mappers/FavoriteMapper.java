package nl.novi.recipeapp.mappers;
import nl.novi.recipeapp.dtos.favorite.FavoriteResponseDto;
import nl.novi.recipeapp.entities.FavoriteEntity;
import org.springframework.stereotype.Component;

@Component
public class FavoriteMapper {

    public FavoriteResponseDto toResponseDto(FavoriteEntity favorite) {

        return new FavoriteResponseDto(
                favorite.getFavoriteId(),
                favorite.getRecipe().getRecipeId(),
                favorite.getRecipe().getTitle(),
                favorite.getCreated_at()
        );
    }
}
