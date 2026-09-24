package org.ong.dryforest.modules.category;


import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.category.dto.CategoryDTO;
import org.ong.dryforest.modules.category.dto.CategoryTranslationDTO;
import org.ong.dryforest.shared.exceptions.ErrorCode;
import org.ong.dryforest.shared.utils.ServiceUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> getAll(Pageable pageable){

        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public CategoryDTO getById(Long id){
        var category = getCategoryOrThrow(id);
        return categoryMapper.toDTO(category);
    }

    public CategoryDTO create(CategoryDTO categoryDTO){
        var category = new Category();
        var saveCategory = categoryRepository.save(category);

        saveTranslations(
                saveCategory,
                categoryDTO.getTranslations()
        ) ;
        return categoryMapper.toDTO(saveCategory);
    }

    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {

        var category = getCategoryOrThrow(id);

        category.getTranslations().clear();

        saveTranslations(
                category,
                categoryDTO.getTranslations()
        );

        categoryRepository.saveAndFlush(category);

        return categoryMapper.toDTO(category);
    }

    public void delete(Long id){
        var category = getCategoryOrThrow(id);
        categoryRepository.delete(category);
    }

    private Category getCategoryOrThrow(Long id) {

        return ServiceUtils.getOrThrow(
                () -> categoryRepository.findById(id),
                ErrorCode.CATEGORY_NOT_FOUND,
                "Category not found with id: " + id
        );
    }

    private void saveTranslations(
            Category category,
            List<CategoryTranslationDTO> translationDTOs
    ) {
        var translations = translationDTOs.stream()
                .map(dto -> categoryMapper.toTranslation(category, dto))
                .toList();

        category.getTranslations().addAll(translations);
    }

}
