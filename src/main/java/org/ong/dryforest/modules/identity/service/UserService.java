package org.ong.dryforest.modules.identity.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.ong.dryforest.enums.Role;
import org.ong.dryforest.modules.identity.User;
import org.ong.dryforest.modules.identity.UserMapper;
import org.ong.dryforest.modules.identity.UserRepository;
import org.ong.dryforest.modules.identity.dto.UserDto;
import org.ong.dryforest.modules.person.Person;
import org.ong.dryforest.modules.person.PersonRepository;
import org.ong.dryforest.shared.repository.RepositoryFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {

    private final RepositoryFactory factory;
    private final UserMapper userMapper;
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    @SneakyThrows(UsernameNotFoundException.class)
    public UserDetails loadUserByUsername(String username) {

        return factory.getUserRepository()
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                username + " not found"
                        )
                );
    }

    public UserDto create(UserDto dto) {

        UserRepository userRepository =
                factory.getUserRepository();

        if (userRepository.existsByUsername(dto.username())) {
            throw new IllegalArgumentException(
                    "Username '" + dto.username() + "' already exists"
            );
        }
        if (dto.password() == null || dto.password().isBlank()) {
            throw new IllegalArgumentException(
                    "Password is required"
            );
        }

        Person person = new Person();

        person.setFirstName(dto.firstName());
        person.setLastName(dto.lastName());
        person.setGender(dto.gender());
        person.setPhoneNumber(dto.phoneNumber());
        person.setEmail(dto.email());
        person.setAddress(dto.address());

        person = personRepository.save(person);

        User user = new User();

        user.setUsername(dto.username());

        user.setPassword(
                passwordEncoder.encode(dto.password())
        );

        user.setSalary(dto.salary());

        user.setPerson(person);

        user.setRole(Role.ADMIN);

        user = userRepository.save(user);

        return userMapper.toDto(user);
    }


    @Transactional(readOnly = true)
    public List<UserDto> findAll() {

        return factory.getUserRepository()
                .findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {

        User user = factory.getUserRepository()
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User with id " + id + " not found"
                        )
                );

        return userMapper.toDto(user);
    }

    public UserDto update(Long id, UserDto dto) {

        UserRepository userRepository =
                factory.getUserRepository();

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User with id " + id + " not found"
                        )
                );

        if (!user.getUsername().equals(dto.username())) {

            if (userRepository.existsByUsername(dto.username())) {
                throw new IllegalArgumentException(
                        "Username '" + dto.username() + "' already exists"
                );
            }

            user.setUsername(dto.username());
        }

        if (dto.password() != null &&
                !dto.password().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(dto.password())
            );
        }

        user.setSalary(dto.salary());

        Person person = user.getPerson();

        person.setFirstName(dto.firstName());
        person.setLastName(dto.lastName());
        person.setGender(dto.gender());
        person.setPhoneNumber(dto.phoneNumber());
        person.setEmail(dto.email());
        person.setAddress(dto.address());

        personRepository.save(person);

        userRepository.save(user);

        return userMapper.toDto(user);
    }


    public void delete(Long id) {

        UserRepository userRepository =
                factory.getUserRepository();

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User with id " + id + " not found"
                        )
                );

        userRepository.delete(user);
    }
}