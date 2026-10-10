
package org.ong.dryforest.modules.category;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.category.dto.CategoryDTO;
import org.ong.dryforest.modules.category.dto.CategoryTranslationDTO;
import org.ong.dryforest.shared.utils.ServiceUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    public CategoryDTO toDTO(Category category) {

        var id = ServiceUtils.requireId(
                category.getId(),
                "Category ID must not be null"
        );

        var translations = category.getTranslations()
                .stream()
                .map(this::translationToDTO)
                .toList();

        var dto = new CategoryDTO(id, translations);

        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());

        return dto;
    }

    public CategoryTranslation toTranslation(
            Category category,
            CategoryTranslationDTO translationDTO
    ) {
        var translation = new CategoryTranslation();

        translation.setLocale(translationDTO.getLocale());
        translation.setName(translationDTO.getName());
        translation.setCategory(category);

        return translation;
    }

    private CategoryTranslationDTO translationToDTO(
            CategoryTranslation translation
    ) {
        var id = ServiceUtils.requireId(
                translation.getId(),
                "CategoryTranslation ID must not be null"
        );

        var dto = new CategoryTranslationDTO(
                id,
                translation.getLocale(),
                translation.getName()
        );

        dto.setCreatedAt(translation.getCreatedAt());
        dto.setUpdatedAt(translation.getUpdatedAt());

        return dto;
    }
}
