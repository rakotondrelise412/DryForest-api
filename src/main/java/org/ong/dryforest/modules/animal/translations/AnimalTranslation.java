package org.ong.dryforest.modules.animal.translations;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.modules.animal.Animal;
import org.ong.dryforest.shared.base.AbstractTranslation;

@Entity
@Table(name = "animal_translations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnimalTranslation extends AbstractTranslation {
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Animal animal;
}
