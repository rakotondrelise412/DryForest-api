
package org.ong.dryforest.modules.identity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.identity.UserSearchQuery;
import org.ong.dryforest.modules.identity.dto.UserDto;
import org.ong.dryforest.modules.identity.service.UserService;
import org.ong.dryforest.shared.search.SearchResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody UserDto dto) {
        return userService.create(dto);
    }

    @GetMapping
    public SearchResult<UserDto> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return userService.findAll(page, size);
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("/search")
    public SearchResult<UserDto> search(
            @RequestBody UserSearchQuery request
    ) {
        return userService.search(request);
    }

    @PutMapping("/{id}")
    public UserDto update(
            @PathVariable Long id,
            @Valid @RequestBody UserDto dto
    ) {
        return userService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
