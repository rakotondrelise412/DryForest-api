package org.ong.dryforest.modules.person;

import org.jspecify.annotations.Nullable;
import org.ong.dryforest.modules.site.Site;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public PersonDTO toDTO(Person person) {

        Long siteId = person.getSite() != null
                ? person.getSite().getId()
                : null;

        PersonDTO dto = new PersonDTO(
                person.getId(),
                person.getLastName(),
                person.getFirstName(),
                person.getEmail(),
                person.getPhoneNumber(),
                person.getAddress(),
                person.getGender(),
                siteId
        );

        dto.setCreatedAt(person.getCreatedAt());
        dto.setUpdatedAt(person.getUpdatedAt());

        return dto;
    }

    public Person toEntity(
            PersonDTO dto,
            @Nullable Site site
    ) {

        Person person = new Person();

        person.setLastName(dto.getLastName());
        person.setFirstName(dto.getFirstName());
        person.setEmail(dto.getEmail());
        person.setPhoneNumber(dto.getPhoneNumber());
        person.setAddress(dto.getAddress());
        person.setGender(dto.getGender());
        person.setSite(site);

        return person;
    }

    public void updateEntity(
            Person person,
            PersonDTO dto,
            @Nullable Site site
    ) {

        person.setLastName(dto.getLastName());
        person.setFirstName(dto.getFirstName());
        person.setEmail(dto.getEmail());
        person.setPhoneNumber(dto.getPhoneNumber());
        person.setAddress(dto.getAddress());
        person.setGender(dto.getGender());
        person.setSite(site);
    }
}