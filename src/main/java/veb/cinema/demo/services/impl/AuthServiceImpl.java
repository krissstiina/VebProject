package veb.cinema.demo.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import veb.cinema.demo.dto.UserRegistrationDto;
import veb.cinema.demo.models.User;
import veb.cinema.demo.models.UserRoles;
import veb.cinema.demo.repositories.UserRepository;
import veb.cinema.demo.repositories.UserRoleRepository;
import veb.cinema.demo.services.AuthService;
import veb.cinema.demo.services.UserService;

@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;

    private UserRoleRepository userRoleRepository;


    private PasswordEncoder passwordEncoder;

    private UserService userService;

    public AuthServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.userService = userService;
    }

    @Override
    public void register(UserRegistrationDto registrationDTO) {
        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
            throw new RuntimeException("passwords.match");
        }

        Optional<User> byEmail = this.userRepository.findByEmail(registrationDTO.getEmail());

        if (byEmail.isPresent()) {
            throw new RuntimeException("email.used");
        }

        var userRole = userRoleRepository.
                findRoleByName(UserRoles.USER).orElseThrow();

        User user = new User(
                registrationDTO.getEmail(),
                passwordEncoder.encode(registrationDTO.getPassword()),
                registrationDTO.getName(),
                registrationDTO.getSurname(),
                registrationDTO.getMiddleName(),
                registrationDTO.getPhoneNumber(),
                registrationDTO.getAge()
        );

        user.setRoles(List.of(userRole));

        this.userRepository.save(user);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByName(username).orElseThrow();
    }
}
