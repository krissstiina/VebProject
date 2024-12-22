package veb.cinema.demo.services;


import org.springframework.security.core.Authentication;
import veb.cinema.demo.dto.UserDto;
import veb.cinema.demo.dto.UserProfileDto;
import veb.cinema.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    
    UserDto findById(int id);
    UserDto update(UserDto userDto);
    List<UserDto> findAll();
    UserProfileDto getUserProfile(String username);
    UserDto findUserByName(String username);
}
