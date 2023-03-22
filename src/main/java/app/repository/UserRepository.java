package app.repository;

import app.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.dni = ?1")
    Optional<User> findUserByDni(String dni);

    @Transactional
    @Modifying
    @Query("UPDATE User u set u.dni = ?1, u.firstName = ?2, u.lastName = ?3, u.password = ?4 WHERE u.userId = ?5")
    void updateUserById(String dni, String firstName, String lastName, String password, Long userId);
}
