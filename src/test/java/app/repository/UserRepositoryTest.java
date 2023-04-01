package app.repository;

import app.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository testRepository;

    @AfterEach
    void tearDown() {
        this.testRepository.deleteAll();
    }

    @Test
    void findByDniTest() {
        User user = new User("43908622", "Martin", "Garabal", "austral");
        this.testRepository.save(user);
        User userByDni = this.testRepository.findByDni("43908622").get();
        assertEquals(userByDni.getDni(), "43908622");
    }

    @Test
    void findByDniNotFoundTest() {
        assertThrows(NoSuchElementException.class, () -> this.testRepository.findByDni("43908622").get());
    }

    @Test
    void findAllEmployees() {
    }

    @Test
    void findAllOwners() {
    }
}
