package org.ong.dryforest.modules.person.mapper;

import org.ong.dryforest.modules.person.Person;
import org.ong.dryforest.modules.person.dto.PersonWebDTO;
import org.ong.dryforest.modules.site.Site;

public class PersonMapper {

    private PersonMapper() {
    }

    public static PersonWebDTO toWebDTO(Person person) {

        Long siteId = null;

        if (person.getSite() != null) {
            siteId = person.getSite().getId();
        }

        return new PersonWebDTO(
                person.getId(),
                person.getLastName(),
                person.getFirstName(),
                person.getEmail(),
                person.getPhoneNumber(),
                person.getAddress(),
                person.getGender(),
                siteId
        );
    }
}
