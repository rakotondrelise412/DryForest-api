
package org.ong.dryforest.modules.person;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ong.dryforest.shared.search.SearchResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;


    @GetMapping
    @PreAuthorize("hasAuthority('PERSON_READ')")
    public SearchResult<PersonDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return personService.findAll(page, size);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PERSON_READ')")
    public PersonDTO findById(
            @PathVariable Long id
    ) {
        return personService.findById(id);
    }


    @PostMapping("/search")
    @PreAuthorize("hasAuthority('PERSON_READ')")
    public SearchResult<PersonDTO> search(
            @RequestBody PersonSearchQuery request
    ) {
        return personService.search(request);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('PERSON_CREATE')")
    public PersonDTO create(
            @Valid @RequestBody PersonDTO dto
    ) {
        return personService.create(dto);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PERSON_UPDATE')")
    public PersonDTO update(
            @PathVariable Long id,
            @Valid @RequestBody PersonDTO dto
    ) {
        return personService.update(id, dto);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('PERSON_DELETE')")
    public void delete(
            @PathVariable Long id
    ) {
        personService.delete(id);
    }
}
