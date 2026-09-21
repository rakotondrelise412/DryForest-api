package org.ong.dryforest.modules.plantation.translations;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.modules.plantation.PlantationBlock;
import org.ong.dryforest.shared.base.AbstractTranslation;

@Entity
@Table(name = "plantation_block_translations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlantationBlockTranslation extends AbstractTranslation {
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private PlantationBlock plantationBlock;
}
