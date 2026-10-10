package org.ong.dryforest.modules.species;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.shared.search.AbstractSearchQuery;
import org.ong.dryforest.shared.search.Searchable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesSearchQuery extends AbstractSearchQuery {

    @Searchable
    private Long id;

    @Searchable(
            type = Searchable.Type.CONTAINS
    )
    private String scientificName;

    @Searchable(
            field = "translations.name",
            type = Searchable.Type.CONTAINS
    )
    private String name;

    @Searchable(
            field = "speciesType.id"
    )
    private Long speciesTypeId;

    @Override
    protected Class<?> getEntityClass() {
        return Species.class;
    }
}