package veb.cinema.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import veb.cinema.demo.dto.UserDto;
import veb.cinema.demo.dto.UserProfileDto;
import veb.cinema.demo.exception.UserNotFoundException;
import veb.cinema.demo.models.User;
import veb.cinema.demo.repositories.UserRepository;
import veb.cinema.demo.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@EnableCaching
public class UserServiceImpl implements UserService {
    private static final String CACHE_KEY = "users";

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto findById(int id) {
        return userRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    @Override
    @CachePut(value = CACHE_KEY)
    public UserDto update(UserDto userDto) {
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());

        userRepository.save(user);

        return convertToDto(user);
    }



    @Override
    @Cacheable(value =CACHE_KEY)
    public UserProfileDto getUserProfile(String username) {
        // Находим пользователя по email (или другому уникальному полю)
        Optional<User> user = userRepository.findByEmail(username);

        if (user.isEmpty()) {
            // Если пользователь не найден, выбрасываем исключение
            throw new UserNotFoundException();
        }

        // Преобразуем сущность User в DTO
        User u = user.get();
        return new UserProfileDto(
                u.getId(),
                u.getEmail(),
                u.getName(),
                u.getSurname(),
                u.getMiddleName(),
                u.getPhoneNumber(),
                u.getAge()
        );
    }

    @Override
    @Cacheable(value = CACHE_KEY)
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(this::convertToUserDto)
                .collect(Collectors.toList());
    }

//    @Override
//    public int getCurrentUserId(Authentication authentication) {
//        if (authentication == null) {
//            throw new SecurityException("Authentication is null");
//        }
//
//        Object principal = authentication.getPrincipal();
//
//        if (principal instanceof UserDto user) {
//            return user.getId();
//        } else {
//            throw new SecurityException("Unsupported Authentication principal type: " + principal.getClass().getName());
//        }
//    }

    @CachePut(value = CACHE_KEY)
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDto findUserByName(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        Optional<User> userOptional = userRepository.findByName(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new UserDto(user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getSurname(),
                    user.getMiddleName(),
                    user.getPhoneNumber(),
                    user.getAge());
        } else {
            throw new RuntimeException("User not found");
        }
    }



    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        return dto;
    }

    private UserDto convertToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        return dto;
    }
}
