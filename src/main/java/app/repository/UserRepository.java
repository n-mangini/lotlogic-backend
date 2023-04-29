package app.repository;

import app.model.User;
import app.model.UserRole;
import app.model.projection.UserProjection;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByDni(String dni);

    //TODO Change object for user
    @Query("SELECT u FROM User u WHERE u.role = 'EMPLOYEE' AND u.active = true")
    List<User> findAllEmployees();

    List<UserProjection> findAllByActiveAndRole(boolean active, UserRole role);

    Optional<User> findUserByDniAndPassword(String dni, String password);

    @Query("SELECT u.role FROM User u WHERE u.dni = ?1")
    String findRoleByDni(String dni);
}
