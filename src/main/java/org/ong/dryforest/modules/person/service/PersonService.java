package org.ong.dryforest.modules.person.service;


import org.ong.dryforest.modules.person.Person;
import org.ong.dryforest.modules.person.dto.PersonDTO;

import java.util.List;

public interface PersonService {

    List<Person> findAllPersons();

    Person findPersonById(Long id);

    Person createPerson(PersonDTO personDTO);

    Person updatePerson(Long id, PersonDTO personDTO);

    void deletePerson(Long id);
}
