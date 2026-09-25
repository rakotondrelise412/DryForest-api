package org.ong.dryforest.modules.person;

import org.ong.dryforest.modules.person.dto.PersonWebDTO;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public PersonWebDTO toWebDTO(Person person) {

        Long siteId = null;

        siteId = person.getSite().getId();

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
