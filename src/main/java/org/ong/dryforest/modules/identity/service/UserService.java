package org.ong.dryforest.modules.identity.service;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.enums.Role;
import org.ong.dryforest.modules.identity.User;
import org.ong.dryforest.modules.identity.UserMapper;
import org.ong.dryforest.modules.identity.UserRepository;
import org.ong.dryforest.modules.identity.dto.UserDto;
import org.ong.dryforest.modules.person.Person;
import org.ong.dryforest.modules.person.PersonRepository;
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

    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {

        return userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                username + " not found"
                        )
                );
    }


    @Transactional
    public UserDto create(UserDto dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {

            throw new IllegalArgumentException(
                    "Username '" +
                            dto.getUsername() +
                            "' already exists"
            );
        }

        if (dto.getPassword() == null ||
                dto.getPassword().isBlank()) {

            throw new IllegalArgumentException(
                    "Password is required"
            );
        }


        Person person = new Person();

        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setGender(dto.getGender());
        person.setPhoneNumber(dto.getPhoneNumber());
        person.setEmail(dto.getEmail());
        person.setAddress(dto.getAddress());

        person = personRepository.save(person);

        User user = new User();

        user.setUsername(dto.getUsername());

        user.setPassword(
                passwordEncoder.encode(
                        dto.getPassword()
                )
        );

        user.setSalary(dto.getSalary());

        user.setPerson(person);

        user.setRole(Role.ADMIN);

        user = userRepository.save(user);


        return userMapper.toDto(user);
    }


    @Transactional(readOnly = true)
    public List<UserDto> findAll() {

        return userRepository
                .findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }


    @Transactional(readOnly = true)
    public UserDto findById(Long id) {

        User user =
                userRepository
                        .findUserById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "User with id " +
                                                id +
                                                " not found"
                                )
                        );

        return userMapper.toDto(user);
    }


    @Transactional
    public UserDto update(
            Long id,
            UserDto dto) {

        User user =
                userRepository
                        .findUserById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "User with id " +
                                                id +
                                                " not found"
                                )
                        );


        if (!user.getUsername()
                .equals(dto.getUsername())) {

            if (userRepository.existsByUsername(
                    dto.getUsername())) {

                throw new IllegalArgumentException(
                        "Username '" +
                                dto.getUsername() +
                                "' already exists"
                );
            }

            user.setUsername(
                    dto.getUsername()
            );
        }


        if (dto.getPassword() != null &&
                !dto.getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(
                            dto.getPassword()
                    )
            );
        }


        user.setSalary(
                dto.getSalary()
        );
        

        Person person = user.getPerson();

        person.setFirstName(
                dto.getFirstName()
        );

        person.setLastName(
                dto.getLastName()
        );

        person.setGender(
                dto.getGender()
        );

        person.setPhoneNumber(
                dto.getPhoneNumber()
        );

        person.setEmail(
                dto.getEmail()
        );

        person.setAddress(
                dto.getAddress()
        );


        personRepository.save(person);
        userRepository.save(user);


        return userMapper.toDto(user);
    }


    @Transactional
    public void delete(Long id) {

        User user =
                userRepository
                        .findUserById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "User with id " +
                                                id +
                                                " not found"
                                )
                        );

        userRepository.delete(user);
    }
}