package org.ong.dryforest.modules.patrol;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.modules.patrol.translations.PatrolGroupTranslation;
import org.ong.dryforest.shared.base.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patrol_groups")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PatrolGroup extends AbstractEntity {
    @OneToMany(mappedBy = "patrolGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatrolGroupTranslation> translations = new ArrayList<>();
}
