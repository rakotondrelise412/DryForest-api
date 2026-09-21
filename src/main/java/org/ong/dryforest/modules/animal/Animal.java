package org.ong.dryforest.modules.animal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.ong.dryforest.modules.animal.translations.AnimalTranslation;
import org.ong.dryforest.modules.category.Category;
import org.ong.dryforest.shared.base.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "animals")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Animal extends AbstractEntity {
    @ManyToOne
    private @Nullable Category category;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnimalTranslation> translations = new ArrayList<>();
}
