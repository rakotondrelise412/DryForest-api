package org.ong.dryforest.modules.person.service;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.person.Person;
import org.ong.dryforest.modules.person.PersonRepository;
import org.ong.dryforest.modules.person.dto.PersonDTO;
import org.ong.dryforest.modules.site.Site;
import org.ong.dryforest.modules.site.SiteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final SiteRepository siteRepository;

    // =========================
    // GET ALL
    // =========================
    @Override
    public List<Person> findAllPersons() {
        return personRepository.findAll();
    }

    // =========================
    // GET BY ID
    // =========================
    @Override
    public Person findPersonById(Long id) {

        return personRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Personne introuvable avec l'id : " + id
                        )
                );
    }

    // =========================
    // CREATE
    // =========================
    @Override
    public Person createPerson(PersonDTO dto) {

        // Vérifier si l'email existe déjà
        if (personRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException(
                    "Une personne avec cet email existe déjà"
            );
        }

        // Création de la personne
        Person person = new Person();

        person.setLastName(dto.getLastName());
        person.setFirstName(dto.getFirstName());
        person.setEmail(dto.getEmail());
        person.setPhoneNumber(dto.getPhoneNumber());
        person.setAddress(dto.getAddress());
        person.setGender(dto.getGender());

        // Associer le site
        if (dto.getSiteId() != null) {

            Site site = siteRepository.findById(dto.getSiteId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Site introuvable avec l'id : "
                                            + dto.getSiteId()
                            )
                    );

            person.setSite(site);
        }

        return personRepository.save(person);
    }

    // =========================
    // UPDATE
    // =========================
    @Override
    public Person updatePerson(Long id, PersonDTO dto) {

        // Chercher la personne
        Person person = findPersonById(id);

        // Mise à jour des informations
        person.setLastName(dto.getLastName());
        person.setFirstName(dto.getFirstName());
        person.setPhoneNumber(dto.getPhoneNumber());
        person.setAddress(dto.getAddress());
        person.setGender(dto.getGender());

        // Mise à jour de l'email
        if (dto.getEmail() != null &&
                !dto.getEmail().equals(person.getEmail())) {

            if (personRepository.existsByEmail(dto.getEmail())) {
                throw new RuntimeException(
                        "Cet email est déjà utilisé"
                );
            }

            person.setEmail(dto.getEmail());
        }

        // Mise à jour du site
        if (dto.getSiteId() != null) {

            Site site = siteRepository.findById(dto.getSiteId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Site introuvable avec l'id : "
                                            + dto.getSiteId()
                            )
                    );

            person.setSite(site);

        } else {
            person.setSite(null);
        }

        return personRepository.save(person);
    }

    // =========================
    // DELETE
    // =========================
    @Override
    public void deletePerson(Long id) {

        // Vérifier que la personne existe
        Person person = findPersonById(id);

        // Suppression physique
        personRepository.delete(person);
    }
}