package org.ong.dryforest.modules.category.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.enums.Locale;
import org.ong.dryforest.shared.base.AbstractTimestamps;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTranslationDTO extends AbstractTimestamps {
    private Long id;
    private Locale locale;
    private String name;
}