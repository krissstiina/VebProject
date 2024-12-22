package veb.cinema.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import veb.cinema.demo.models.Role;
import veb.cinema.demo.models.UserRoles;

import java.util.Optional;


@Repository
public interface UserRoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findRoleByName(UserRoles role);
}
