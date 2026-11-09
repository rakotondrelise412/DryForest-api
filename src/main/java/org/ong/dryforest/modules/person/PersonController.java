
package org.ong.dryforest.modules.person;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    @PreAuthorize("@permissionService.hasPermission(T(org.ong.dryforest.enums.Permission).PERSON_READ)")
    public List<PersonDTO> findAll() {

        return personService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@permissionService.hasPermission(T(org.ong.dryforest.enums.Permission).PERSON_READ)")
    public PersonDTO findById(
            @PathVariable Long id
    ) {
        return personService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("@permissionService.hasPermission(T(org.ong.dryforest.enums.Permission).PERSON_CREATE)")
    public PersonDTO create(
            @RequestBody PersonDTO dto
    ) {
        return personService.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@permissionService.hasPermission(T(org.ong.dryforest.enums.Permission).PERSON_UPDATE)")
    public PersonDTO update(
            @PathVariable Long id,
            @RequestBody PersonDTO dto
    ) {
        return personService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@permissionService.hasPermission(T(org.ong.dryforest.enums.Permission).PERSON_DELETE)")
    public void delete(
            @PathVariable Long id
    ) {
        personService.delete(id);
    }
}
