package app.repository;

import app.model.User;
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

    @Query("SELECT u FROM User u WHERE u.role = 'OWNER' AND u.active = true")
    List<User> findAllOwners();

    Optional<User> findUserByDniAndPassword(String dni, String password);

    @Query("SELECT u.role FROM User u WHERE u.dni = ?1")
    String findRoleByDni(String dni);
}
