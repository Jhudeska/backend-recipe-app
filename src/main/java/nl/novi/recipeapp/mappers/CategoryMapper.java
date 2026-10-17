package nl.novi.recipeapp.mappers;

import nl.novi.recipeapp.dtos.CategoryDto;
import nl.novi.recipeapp.dtos.CategoryRequestDto;
import nl.novi.recipeapp.entities.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto toDto(CategoryEntity category) {

        return new CategoryDto(
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
