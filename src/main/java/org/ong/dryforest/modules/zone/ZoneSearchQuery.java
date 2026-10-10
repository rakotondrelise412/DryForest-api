package org.ong.dryforest.modules.zone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.shared.search.AbstractSearchQuery;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZoneSearchQuery extends AbstractSearchQuery {

    private Double area;

    private UUID uuid;

    @Override
    protected Class<?> getEntityClass() {
        return Zone.class;
    }
}
