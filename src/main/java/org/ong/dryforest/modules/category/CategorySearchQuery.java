
package org.ong.dryforest.modules.category;

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
public class CategorySearchQuery extends AbstractSearchQuery {

    @Searchable
    private Long id;

    @Searchable(
            field = "translations.name",
            type = Searchable.Type.CONTAINS
    )
    private String name;

    @Override
    protected Class<?> getEntityClass() {
        return Category.class;
    }
}
