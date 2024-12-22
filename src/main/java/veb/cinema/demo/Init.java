package veb.cinema.demo;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import veb.cinema.demo.models.Role;
import veb.cinema.demo.models.User;
import veb.cinema.demo.models.UserRoles;
import veb.cinema.demo.repositories.UserRepository;
import veb.cinema.demo.repositories.UserRoleRepository;


import java.util.List;

@Component
public class Init implements CommandLineRunner {
    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final String defaultPassword;

    public Init(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, @Value("${app.default.password}") String defaultPassword) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultPassword = defaultPassword;
    }

    @Override
    public void run(String... args) throws Exception {
        initRoles();
        initUsers();
    }

    private void initRoles() {
        if (userRoleRepository.count() == 0) {
            var adminRole = new Role(UserRoles.ADMIN);
            var normalUserRole = new Role(UserRoles.USER);
            userRoleRepository.save(adminRole);
            userRoleRepository.save(normalUserRole);
        }
    }

    private void initUsers() {
//        initNormalUser();
        if (userRepository.count() == 0) {
            initAdmin();
            initNormalUser();
        }
    }

    private void initAdmin(){
            var adminRole = userRoleRepository.
                    findRoleByName(UserRoles.ADMIN).orElseThrow();
            var adminUser = new User("admin@example.com", passwordEncoder.encode(defaultPassword), "admin", "Admin", "Admin", "89042962034", 30);
            adminUser.setRoles(List.of(adminRole));
            userRepository.save(adminUser);
    }


    private void initNormalUser(){
        var userRole = userRoleRepository.
                findRoleByName(UserRoles.USER).orElseThrow();

        var normalUser = new User("ksusha@mail.ru",passwordEncoder.encode(defaultPassword),"ksusha", "Gruzdeva" , "Alexsandrovna","89058765432", 19);
        normalUser.setRoles(List.of(userRole));

        userRepository.save(normalUser);
    }
}
