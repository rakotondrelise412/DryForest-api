package org.ong.dryforest.modules.person.controller;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.person.Person;
import org.ong.dryforest.modules.person.dto.PersonDTO;
import org.ong.dryforest.modules.person.dto.PersonWebDTO;
import org.ong.dryforest.modules.person.mapper.PersonMapper;
import org.ong.dryforest.modules.person.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public ResponseEntity<List<PersonWebDTO>> getAllPersons() {

        List<PersonWebDTO> persons = personService
                .findAllPersons()
                .stream()
                .map(PersonMapper::toWebDTO)
                .toList();

        return ResponseEntity.ok(persons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonWebDTO> getPersonById(
            @PathVariable Long id) {

        Person person = personService.findPersonById(id);

        return ResponseEntity.ok(
                PersonMapper.toWebDTO(person)
        );
    }

    @PostMapping
    public ResponseEntity<PersonWebDTO> createPerson(
            @RequestBody PersonDTO personDTO) {

        Person person = personService.createPerson(personDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PersonMapper.toWebDTO(person));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonWebDTO> updatePerson(
            @PathVariable Long id,
            @RequestBody PersonDTO personDTO) {

        Person person = personService.updatePerson(id, personDTO);

        return ResponseEntity.ok(
                PersonMapper.toWebDTO(person)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(
            @PathVariable Long id) {

        personService.deletePerson(id);

        return ResponseEntity.noContent().build();
    }
}
