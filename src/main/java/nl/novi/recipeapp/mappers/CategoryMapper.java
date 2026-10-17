package nl.novi.recipeapp.mappers;
import nl.novi.recipeapp.dtos.category.CategoryRequestDto;
import nl.novi.recipeapp.dtos.category.CategoryResponseDto;
import nl.novi.recipeapp.entities.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponseDto toResponseDto(CategoryEntity category) {

        return new CategoryResponseDto(
                category.getCategoryId(),
                category.getName(),
                category.getDescription()
        );
    }

    public CategoryEntity toEntity(CategoryRequestDto dto) {

        return new CategoryEntity(
                dto.name(),
                dto.description()
        );
    }
}
