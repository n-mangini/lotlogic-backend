package app.repository;

import app.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE User u set u.dni = ?2, u.firstName = ?3, u.lastName = ?4, u.password = ?5 WHERE u.userId = ?1")
    void updateUserById(Long userId, String dni, String firstName, String lastName, String password);

    Optional<User> findByDni(String dni);

    //TODO Change object for user
    @Query("SELECT u.userId, u.firstName, u.lastName FROM User u WHERE u.role = 'EMPLOYEE'")
    List<Object> findAllEmployees();

    @Query("SELECT u.userId, u.firstName, u.lastName FROM User u WHERE u.role = 'OWNER'")
    List<Object> findAllOwners();
}
