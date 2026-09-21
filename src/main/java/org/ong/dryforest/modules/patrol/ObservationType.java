package org.ong.dryforest.modules.patrol;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.ong.dryforest.modules.patrol.translations.ObservationTypeTranslation;
import org.ong.dryforest.shared.base.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "observation_types")
public class ObservationType extends AbstractEntity {
    @OneToMany(mappedBy = "observationType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ObservationTypeTranslation> translations = new ArrayList<>();
}
