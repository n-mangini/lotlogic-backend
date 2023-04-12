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
    @Query("SELECT u.userId, u.firstName, u.lastName FROM User u WHERE u.role = 'EMPLOYEE'")
    List<Object> findAllEmployees();

    @Query("SELECT u.userId, u.firstName, u.lastName FROM User u WHERE u.role = 'OWNER'")
    List<Object> findAllOwners();

    Optional<User> findUserByDniAndPassword(String dni, String password);

    @Query("SELECT u.role FROM User u WHERE u.dni = ?1")
    String findRoleById(String dni);
}
