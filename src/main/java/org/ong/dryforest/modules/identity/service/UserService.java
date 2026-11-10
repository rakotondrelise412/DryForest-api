
package org.ong.dryforest.modules.identity.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.ong.dryforest.enums.Role;
import org.ong.dryforest.modules.identity.User;
import org.ong.dryforest.modules.identity.UserMapper;
import org.ong.dryforest.modules.identity.UserRepository;
import org.ong.dryforest.modules.identity.UserSearchQuery;
import org.ong.dryforest.modules.identity.dto.UserDto;
import org.ong.dryforest.modules.person.Person;
import org.ong.dryforest.modules.person.PersonRepository;
import org.ong.dryforest.modules.site.Site;
import org.ong.dryforest.modules.site.SiteRepository;
import org.ong.dryforest.shared.search.SearchResult;
import org.ong.dryforest.shared.search.SearchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PersonRepository personRepository;
    private final SiteRepository siteRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {

        return userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Utilisateur introuvable : " + username
                        )
                );
    }

    @Transactional
    public UserDto create(UserDto dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException(
                    "Le nom d'utilisateur '" + dto.getUsername() + "' existe déjà."
            );
        }

        if (personRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException(
                    "Une personne avec l'adresse email '"
                            + dto.getEmail()
                            + "' existe déjà."
            );
        }

        String password = dto.getPassword();

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException(
                    "Le mot de passe est obligatoire."
            );
        }

        Person person = new Person();

        person.setFirstName(dto.getFirstName());
        person.setLastName(normalizeLastName(dto.getLastName()));
        person.setGender(dto.getGender());
        person.setPhoneNumber(dto.getPhoneNumber());
        person.setEmail(dto.getEmail());
        person.setAddress(dto.getAddress());

        assignSite(person, dto.getRole(), dto.getSiteId());

        person = personRepository.save(person);

        User user = new User();

        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(password));
        user.setSalary(dto.getSalary());
        user.setPerson(person);
        user.setRole(dto.getRole());

        user = userRepository.save(user);

        return userMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    public SearchResult<UserDto> findAll(int page, int size) {

        validatePagination(page, size, false);

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<User> result =
                userRepository.findAllByIsDeletedFalse(pageable);

        return toSearchResult(result, page, size);
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {

        User user = getActiveUser(id);

        return userMapper.toDto(user);
    }

    @Transactional
    public UserDto update(Long id, UserDto dto) {

        User user = getActiveUser(id);

        if (!user.getUsername().equals(dto.getUsername())
                && userRepository.existsByUsername(dto.getUsername())) {

            throw new IllegalArgumentException(
                    "Le nom d'utilisateur '" + dto.getUsername() + "' existe déjà."
            );
        }

        user.setUsername(dto.getUsername());
        user.setSalary(dto.getSalary());
        user.setRole(dto.getRole());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        Person person = user.getPerson();

        if (!person.getEmail().equalsIgnoreCase(dto.getEmail())
                && personRepository.existsByEmail(dto.getEmail())) {

            throw new IllegalArgumentException(
                    "Une personne avec l'adresse email '"
                            + dto.getEmail()
                            + "' existe déjà."
            );
        }

        person.setFirstName(dto.getFirstName());
        person.setLastName(normalizeLastName(dto.getLastName()));
        person.setGender(dto.getGender());
        person.setPhoneNumber(dto.getPhoneNumber());
        person.setEmail(dto.getEmail());
        person.setAddress(dto.getAddress());

        assignSite(person, dto.getRole(), dto.getSiteId());

        personRepository.save(person);

        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional
    public void delete(Long id) {

        User user = getActiveUser(id);

        user.setDeleted(true);

        userRepository.save(user);
    }

    private User getActiveUser(Long id) {

        return userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Utilisateur introuvable avec l'identifiant : " + id
                        )
                );
    }

    private void assignSite(
            Person person,
            Role role,
            @Nullable Long siteId
    ) {

        if (siteId == null) {

            if (role != Role.ADMIN) {
                throw new IllegalArgumentException(
                        "Un site est obligatoire pour le rôle " + role + "."
                );
            }

            person.setSite(null);
            return;
        }

        Site site = siteRepository.findByIdAndIsDeletedFalse(siteId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Site introuvable ou supprimé : " + siteId
                        )
                );

        person.setSite(site);
    }

    @Nullable
    private String normalizeLastName(@Nullable String lastName) {

        if (lastName == null || lastName.isBlank()) {
            return null;
        }

        return lastName.trim();
    }

    @Transactional(readOnly = true)
    public SearchResult<UserDto> search(UserSearchQuery request) {

        int page = request.resolvePageNumber();
        int size = request.resolvePageSize();

        validatePagination(page, size, true);

        String sortBy = request.resolveSortBy();

        Sort.Direction direction = request.resolveAscending()
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(
                page - 1,
                size,
                Sort.by(direction, sortBy)
        );

        Specification<User> specification =
                SearchSpecification.from(request);

        specification = specification.and(
                (root, query, cb) ->
                        cb.isFalse(root.get("isDeleted"))
        );

        Page<User> result = userRepository.findAll(
                specification,
                pageable
        );

        return toSearchResult(result, page, size);
    }

    private void validatePagination(
            int page,
            int size,
            boolean oneBased
    ) {

        int minimumPage = oneBased ? 1 : 0;

        if (page < minimumPage || size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "Paramètres de pagination invalides."
            );
        }
    }

    private SearchResult<UserDto> toSearchResult(
            Page<User> result,
            int page,
            int size
    ) {

        List<UserDto> content = result.getContent()
                .stream()
                .map(userMapper::toDto)
                .toList();

        return new SearchResult<>(
                content,
                result.getTotalElements(),
                result.getTotalPages(),
                page,
                size,
                result.hasNext(),
                result.hasPrevious()
        );
    }
}
