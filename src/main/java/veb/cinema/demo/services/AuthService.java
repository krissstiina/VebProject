package veb.cinema.demo.services;

import veb.cinema.demo.dto.UserRegistrationDto;
import veb.cinema.demo.models.User;

import java.security.Principal;

public interface AuthService {
    void register(UserRegistrationDto registrationDTO);
    User getUser(String username);
}
