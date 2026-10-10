
package org.ong.dryforest.modules.category;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.category.dto.CategoryDTO;
import org.ong.dryforest.modules.category.dto.CategoryTranslationDTO;
import org.ong.dryforest.shared.exceptions.ErrorCode;
import org.ong.dryforest.shared.search.SearchResult;
import org.ong.dryforest.shared.search.SearchSpecification;
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
    public Page<CategoryDTO> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public CategoryDTO getById(Long id) {
        return categoryMapper.toDTO(getCategoryOrThrow(id));
    }

    public CategoryDTO create(CategoryDTO categoryDTO) {
        Category category = new Category();

        saveTranslations(category, categoryDTO.getTranslations());

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toDTO(savedCategory);
    }

    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        Category category = getCategoryOrThrow(id);

        category.getTranslations().clear();
        saveTranslations(category, categoryDTO.getTranslations());

        categoryRepository.flush();

        return categoryMapper.toDTO(category);
    }

    public void delete(Long id) {
        Category category = getCategoryOrThrow(id);
        categoryRepository.delete(category);
    }


    @Transactional(readOnly = true)
    public SearchResult<CategoryDTO> search(CategorySearchQuery request) {
        return SearchSpecification
                .filterBy(categoryRepository, request)
                .map(categoryMapper::toDTO);
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
            List<CategoryTranslationDTO> translationDTOs) {

        translationDTOs.stream()
                .map(dto -> categoryMapper.toTranslation(category, dto))
                .forEach(category.getTranslations()::add);
    }
}
