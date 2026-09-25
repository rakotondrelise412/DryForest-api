package org.ong.dryforest.modules.person;

import org.ong.dryforest.modules.site.Site;

public class PersonMapper {

    private PersonMapper() {
    }

    public static PersonDTO toDTO(Person person) {

        Long siteId = person.getSite() != null
                ? person.getSite().getId()
                : null;

        return new PersonDTO(
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

    public static Person toEntity(PersonDTO dto, Site site) {

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

    public static void updateEntity(
            Person person,
            PersonDTO dto,
            Site site
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