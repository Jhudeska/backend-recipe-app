package nl.novi.recipeapp.services;

import org.springframework.transaction.annotation.Transactional;
import nl.novi.recipeapp.dtos.CategoryDto;
import nl.novi.recipeapp.dtos.CategoryRequestDto;
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

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long id) {



        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Categorie niet gevonden met ID: " + id
                        )
                );
        return categoryMapper.toDto(category);
    }

    public CategoryDto createCategory(CategoryRequestDto categoryRequestDto) {
        if (categoryRepository.existsByName(categoryRequestDto.name())) {
            throw new IllegalArgumentException(
                    "Deze categorienaam bestaat al: " + categoryRequestDto.name()
            );
        }
        CategoryEntity category = categoryMapper.toEntity(categoryRequestDto);
        CategoryEntity savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);


    }

    public CategoryDto updateCategory(
            Long id,
            CategoryRequestDto categoryRequestDto) {

        CategoryEntity existingCategory = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Categorie niet gevonden met ID: " + id
                        )
                );

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

        return categoryMapper.toDto(savedCategory);
    }

    public void deleteCategory(Long id) {
        CategoryEntity existingCategory =
                categoryRepository.findById(id)
                        .orElseThrow( () ->
                                new RuntimeException(
                                        "Categorie niet gevonden met ID: + id"
                                ));

        categoryRepository.delete(existingCategory);
    }
}
