package org.ong.dryforest.modules.person;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public List<PersonDTO> findAll() {

        return personService.findAll();
    }

    @GetMapping("/{id}")
    public PersonDTO findById(
            @PathVariable Long id
    ) {
        return personService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDTO create(
            @RequestBody PersonDTO dto
    ) {
        return personService.create(dto);
    }

    @PutMapping("/{id}")
    public PersonDTO update(
            @PathVariable Long id,
            @RequestBody PersonDTO dto
    ) {
        return personService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id
    ) {
        personService.delete(id);
    }
}
