package nl.novi.recipeapp.services;

import nl.novi.recipeapp.dtos.category.CategoryResponseDto;
import org.springframework.transaction.annotation.Transactional;
import nl.novi.recipeapp.dtos.category.CategoryRequestDto;
import nl.novi.recipeapp.entities.CategoryEntity;
import nl.novi.recipeapp.mappers.CategoryMapper;
import nl.novi.recipeapp.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponseDto getCategoryById(Long id) {



        CategoryEntity category = findCategoryById(id);
        return categoryMapper.toResponseDto(category);
    }

    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {

        if (categoryRepository.existsByName(categoryRequestDto.name())) {
            throw new IllegalArgumentException(
                    "Deze categorienaam bestaat al: " + categoryRequestDto.name()
            );
        }
        CategoryEntity category = categoryMapper.toEntity(categoryRequestDto);
        CategoryEntity savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponseDto(savedCategory);


    }

    public CategoryResponseDto updateCategory(
            Long id,
            CategoryRequestDto categoryRequestDto) {

        CategoryEntity existingCategory = findCategoryById(id);

        boolean nameChanged =
                !existingCategory.getName().equals(categoryRequestDto.name());

        if (nameChanged
                && categoryRepository.existsByName(categoryRequestDto.name())) {
            throw new IllegalArgumentException(
                    "Deze categorienaam bestaat al: " + categoryRequestDto.name()
            );
        }

        existingCategory.setName(categoryRequestDto.name());
        existingCategory.setDescription(categoryRequestDto.description());



        CategoryEntity savedCategory = categoryRepository.save(existingCategory);

        return categoryMapper.toResponseDto(savedCategory);
    }

    public void deleteCategory(Long id) {
        CategoryEntity existingCategory = findCategoryById(id);
        categoryRepository.delete(existingCategory);
    }

    private CategoryEntity findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Categorie niet gevonden met ID: " + id
                        )
                );
    }
}
