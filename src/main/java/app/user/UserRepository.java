package app.user;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.dni = ?1")
    Optional<User> findUserByDni(String dni);

    @Transactional
    @Modifying
    @Query("UPDATE User u set u.dni = ?1, u.name = ?2, u.surname = ?3, u.password = ?4 WHERE u.userId = ?5")
    void updateUserById(String dni, String name, String surname, String password, Integer userId);
}
