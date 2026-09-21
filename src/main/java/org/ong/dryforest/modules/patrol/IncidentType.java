package org.ong.dryforest.modules.patrol;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.modules.patrol.translations.IncidentTypeTranslation;
import org.ong.dryforest.shared.base.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "incident_types")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IncidentType extends AbstractEntity {
    @OneToMany(mappedBy = "incidentType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IncidentTypeTranslation> translations = new ArrayList<>();
}
