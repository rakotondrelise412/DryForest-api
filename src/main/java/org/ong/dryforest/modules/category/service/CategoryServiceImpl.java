package org.ong.dryforest.modules.category.service;


import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.category.Category;
import org.ong.dryforest.modules.category.CategoryRepository;
import org.ong.dryforest.modules.category.dto.CategoryDTO;
import org.ong.dryforest.modules.category.dto.CategoryTranslationDTO;
import org.ong.dryforest.modules.category.translations.CategoryTranslation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDTO> getAll(){
        return categoryRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryDTO getById(Long id){
       Category category = categoryRepository.findById(id)
               .orElseThrow(() ->
                       new RuntimeException(
                               "Categories not found with id" + id
                       ));
       return toDTO(category);
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {

        Category category = new Category();

        saveTranslations(
                category,
                categoryDTO.getTranslations()
        );

        Category savedCategory =
                categoryRepository.save(category);

        return toDTO(savedCategory);
    }

    @Override
    public CategoryDTO update(
            Long id,
            CategoryDTO categoryDTO
    ) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Category not found with id: " + id
                ));

        category.getTranslations().clear();

        saveTranslations(
                category,
                categoryDTO.getTranslations()
        );

        Category updatedCategory =
                categoryRepository.save(category);

        return toDTO(updatedCategory);
    }

    @Override
    public void delete(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Categories not found with id:" + id)
                );
        categoryRepository.delete(category);
    }

    private void saveTranslations(
            Category category,
            List<CategoryTranslationDTO> translationDTOs
    ) {

        if (translationDTOs.isEmpty()) {
            return;
        }

        for (CategoryTranslationDTO translationDTO : translationDTOs) {

            CategoryTranslation translation =
                    new CategoryTranslation();

            translation.setLocale(
                    translationDTO.getLocale()
            );

            translation.setName(
                    translationDTO.getName()
            );

            translation.setCategory(category);

            category.getTranslations().add(translation);
        }
    }

    private CategoryDTO toDTO(Category category) {

        List<CategoryTranslationDTO> translations = category.getTranslations()
                .stream()
                .map(this::toTranslationDTO)
                .toList();

        assert category.getId() != null;
        return new CategoryDTO(
                category.getId(),
                translations
        );
    }

    private CategoryTranslationDTO toTranslationDTO(CategoryTranslation translation) {

        assert translation.getId() != null;
        return new CategoryTranslationDTO(
                translation.getId(),
                translation.getLocale(),
                translation.getName()
        );
    }
}
