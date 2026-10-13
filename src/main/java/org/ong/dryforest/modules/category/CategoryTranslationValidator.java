package org.ong.dryforest.modules.category;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.enums.Locale;
import org.ong.dryforest.modules.category.dto.CategoryTranslationDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryTranslationValidator {

    private final CategoryRepository categoryRepository;

    public void validateUniqueTranslationsForCreate(
            List<CategoryTranslationDTO> translationDTOs
    ) {
        for (CategoryTranslationDTO dto : translationDTOs) {
            if (dto.getName().isBlank()) {
                continue;
            }

            String name = dto.getName().trim();

            if (categoryRepository.existsDuplicateTranslation(name)) {
                throw new IllegalArgumentException(
                        "Le nom '" + name
                                + "' existe déjà dans une catégorie."
                );
            }
        }
    }


    public void validateUniqueTranslationsForUpdate(
            Category category,
            List<CategoryTranslationDTO> translationDTOs,
             Long categoryId
    ) {
        for (CategoryTranslationDTO dto : translationDTOs) {
            if (dto.getName().isBlank()) {
                continue;
            }

            String name = dto.getName().trim();

            CategoryTranslation existingTranslation =
                    category.getTranslations().stream()
                            .filter(t -> t.getLocale() == dto.getLocale())
                            .findFirst()
                            .orElse(null);

            if (existingTranslation != null
                    && existingTranslation.getName().trim()
                    .equalsIgnoreCase(name)) {
                continue;
            }

            if (categoryRepository.existsDuplicateTranslationForUpdate(
                    name, categoryId)) {
                throw new IllegalArgumentException(
                        "Le nom '" + name
                                + "' existe déjà dans une autre catégorie."
                );
            }
        }
    }


    public void ensureFrenchTranslation(Category category) {
        boolean frenchExists = category.getTranslations().stream()
                .anyMatch(t ->
                        t.getLocale() == Locale.FR
                                && !t.getName().isBlank()
                );

        if (!frenchExists) {
            throw new IllegalArgumentException(
                    "La traduction française (FR) est obligatoire."
            );
        }
    }
}
