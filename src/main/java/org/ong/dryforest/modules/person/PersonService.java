package org.ong.dryforest.modules.person;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.ong.dryforest.modules.site.Site;
import org.ong.dryforest.modules.site.SiteRepository;
import org.ong.dryforest.shared.search.SearchResult;
import org.ong.dryforest.shared.search.SearchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonService {

    private final PersonRepository personRepository;
    private final SiteRepository siteRepository;
    private final PersonMapper personMapper;


    @Transactional(readOnly = true)
    public SearchResult<PersonDTO> findAll(int page, int size) {

        validatePagination(page, size, false);

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<Person> result =
                personRepository.findAllByIsDeletedFalse(pageable);

        return toSearchResult(result, page, size);
    }


    @Transactional(readOnly = true)
    public PersonDTO findById(Long id) {

        return personMapper.toDTO(getPerson(id));
    }


    public PersonDTO create(PersonDTO dto) {

        if (personRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Une personne avec cet email existe déjà : "
                            + dto.getEmail()
            );
        }

        Site site = getSite(dto.getSiteId());

        Person person = personMapper.toEntity(dto, site);

        Person savedPerson = personRepository.save(person);

        return personMapper.toDTO(savedPerson);
    }


    public PersonDTO update(Long id, PersonDTO dto) {

        Person person = getPerson(id);

        if (!person.getEmail().equalsIgnoreCase(dto.getEmail())
                && personRepository.existsByEmail(dto.getEmail())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Une autre personne utilise déjà cet email : "
                            + dto.getEmail()
            );
        }

        Site site = getSite(dto.getSiteId());

        personMapper.updateEntity(person, dto, site);

        Person updatedPerson = personRepository.save(person);

        return personMapper.toDTO(updatedPerson);
    }


    public void delete(Long id) {

        Person person = getPerson(id);
        person.setDeleted(true);

        personRepository.save(person);
    }


    @Transactional(readOnly = true)
    public SearchResult<PersonDTO> search(PersonSearchQuery request) {

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

        Specification<Person> specification =
                SearchSpecification.from(request);

        specification = specification.and(
                (root, query, cb) ->
                        cb.isFalse(root.get("isDeleted"))
        );

        Page<Person> result = personRepository.findAll(
                specification,
                pageable
        );

        return toSearchResult(result, page, size);
    }


    private Person getPerson(Long id) {

        return personRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Personne introuvable avec l'id : " + id
                ));
    }


    @Nullable
    private Site getSite(@Nullable Long siteId) {

        if (siteId == null) {
            return null;
        }

        return siteRepository.findByIdAndIsDeletedFalse(siteId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Site introuvable avec l'id : " + siteId
                ));
    }


    private void validatePagination(
            int page,
            int size,
            boolean oneBased
    ) {

        int minimumPage = oneBased ? 1 : 0;

        if (page < minimumPage || size < 1 || size > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Paramètres de pagination invalides."
            );
        }
    }


    private SearchResult<PersonDTO> toSearchResult(
            Page<Person> result,
            int page,
            int size
    ) {

        List<PersonDTO> content = result.getContent()
                .stream()
                .map(personMapper::toDTO)
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