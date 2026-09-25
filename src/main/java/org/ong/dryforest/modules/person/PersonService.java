package org.ong.dryforest.modules.person;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.site.Site;
import org.ong.dryforest.modules.site.SiteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonService {

    private final PersonRepository personRepository;
    private final SiteRepository siteRepository;

    // =========================
    // GET ALL
    // =========================
    @Transactional(readOnly = true)
    public List<PersonDTO> findAll() {

        return personRepository.findAllByIsDeletedFalse()
                .stream()
                .map(PersonMapper::toDTO)
                .toList();
    }

    // =========================
    // GET BY ID
    // =========================
    @Transactional(readOnly = true)
    public PersonDTO findById(Long id) {

        Person person = personRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Personne introuvable avec l'id : " + id
                        )
                );

        return PersonMapper.toDTO(person);
    }

    // =========================
    // CREATE
    // =========================
    public PersonDTO create(PersonDTO dto) {

        if (personRepository.existsByEmailAndIsDeletedFalse(dto.getEmail())) {

            throw new IllegalArgumentException(
                    "Une personne avec cet email existe déjà : "
                            + dto.getEmail()
            );
        }

        Site site = getSite(dto.getSiteId());

        Person person = PersonMapper.toEntity(dto, site);

        Person savedPerson = personRepository.save(person);

        return PersonMapper.toDTO(savedPerson);
    }

    // =========================
    // UPDATE
    // =========================
    public PersonDTO update(Long id, PersonDTO dto) {

        Person person = personRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Personne introuvable avec l'id : " + id
                        )
                );

        if (!person.getEmail().equals(dto.getEmail())
                && personRepository.existsByEmailAndIsDeletedFalse(dto.getEmail())) {

            throw new IllegalArgumentException(
                    "Une autre personne utilise déjà cet email : "
                            + dto.getEmail()
            );
        }

        Site site = getSite(dto.getSiteId());

        PersonMapper.updateEntity(
                person,
                dto,
                site
        );

        Person updatedPerson = personRepository.save(person);

        return PersonMapper.toDTO(updatedPerson);
    }

    // =========================
    // DELETE
    // =========================
    public void delete(Long id) {

        Person person = personRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Personne introuvable avec l'id : " + id
                        )
                );

        /*
         * À adapter selon le nom réel du champ
         * dans AbstractEntity.
         */
        person.setDeleted(true);

        personRepository.save(person);
    }

    // =========================
    // GET SITE
    // =========================
    private Site getSite(Long siteId) {

        if (siteId == null) {
            return null;
        }

        return siteRepository
                .findById(siteId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Site introuvable avec l'id : " + siteId
                        )
                );
    }
}