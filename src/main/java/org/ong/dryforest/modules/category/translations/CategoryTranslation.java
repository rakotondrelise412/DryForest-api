package org.ong.dryforest.modules.category.translations;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.modules.category.Category;
import org.ong.dryforest.shared.base.AbstractTranslation;

@Entity
@Table(name = "category_translations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryTranslation extends AbstractTranslation {
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;
}
