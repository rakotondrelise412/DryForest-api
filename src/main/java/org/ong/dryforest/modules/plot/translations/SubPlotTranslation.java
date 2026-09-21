package org.ong.dryforest.modules.plot.translations;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.modules.plot.SubPlot;
import org.ong.dryforest.shared.base.AbstractTranslation;

@Entity
@Table(name = "sub_plot_translations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubPlotTranslation extends AbstractTranslation {
    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private SubPlot subPlot;
}
