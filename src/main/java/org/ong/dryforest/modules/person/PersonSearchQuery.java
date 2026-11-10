package org.ong.dryforest.modules.person;

import lombok.Getter;
import lombok.Setter;
import org.ong.dryforest.enums.Gender;
import org.ong.dryforest.shared.search.AbstractSearchQuery;
import org.ong.dryforest.shared.search.Searchable;

@Getter
@Setter
public class PersonSearchQuery extends AbstractSearchQuery {

    @Searchable
    private Long id;

    @Searchable(type = Searchable.Type.CONTAINS)
    private String firstName;

    @Searchable(type = Searchable.Type.CONTAINS)
    private String lastName;

    @Searchable(type = Searchable.Type.CONTAINS)
    private String email;

    @Searchable(type = Searchable.Type.CONTAINS)
    private String phoneNumber;

    @Searchable
    private Gender gender;

    @Searchable(field = "site.id")
    private Long siteId;

    @Override
    public Class<Person> getEntityClass() {
        return Person.class;
    }
}