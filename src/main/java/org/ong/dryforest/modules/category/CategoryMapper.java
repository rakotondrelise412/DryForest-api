package org.ong.dryforest.modules.category;

import org.ong.dryforest.modules.category.dto.CategoryDTO;
import org.ong.dryforest.modules.category.dto.CategoryTranslationDTO;
import org.ong.dryforest.modules.category.translations.CategoryTranslation;
import org.ong.dryforest.shared.utils.ServiceUtils;
import org.springframework.stereotype.Component;

@Component
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

        return new CategoryDTO(
                id,
                translations
        );
    }

    public CategoryTranslation toTranslation(
            Category category,
            CategoryTranslationDTO translationDTO
    ){
        var translations = new CategoryTranslation();

        translations.setLocale(
                translationDTO.getLocale()
        );
        translations.setName(
                translationDTO.getName()
        );
        translations.setCategory(category);

        return translations;
    }

    private CategoryTranslationDTO translationToDTO(
            CategoryTranslation translation
    ) {

        var id = ServiceUtils.requireId(
                translation.getId(),
                "CategoryTranslation ID must not be null"
        );

        return new CategoryTranslationDTO(
                id,
                translation.getLocale(),
                translation.getName()
        );
    }
}
