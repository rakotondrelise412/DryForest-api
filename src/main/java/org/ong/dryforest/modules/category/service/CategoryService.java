package org.ong.dryforest.modules.category.service;

import org.ong.dryforest.modules.category.dto.CategoryDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAll();

    CategoryDTO getById(Long id);

    CategoryDTO create(CategoryDTO categoryDTO);

    CategoryDTO update(
            Long id,
            CategoryDTO categoryDTO
    );

    void delete(Long id);
}
