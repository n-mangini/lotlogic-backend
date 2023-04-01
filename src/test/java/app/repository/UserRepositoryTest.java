package app.repository;

import app.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository testRepository;

    @Test
    public void addUserTest() {
        User user = new User("43908622", "Martin", "Garabal", "austral");
        assertTrue(this.testRepository.findByDni(user.getDni()).isEmpty());
        this.testRepository.save(user);
        assertTrue(this.testRepository.findByDni(user.getDni()).isPresent());
    }

    @Test
    void updateUserById() {
        User user = new User("43400400", "Martin", "Garabal", "austral");
        this.testRepository.save(user);
        final User userByDni = this.testRepository.findByDni("43400400").get();
        userByDni.setDni("23200200");
        userByDni.setFirstName("Martin");
        userByDni.setLastName("Demichelis");
        userByDni.setPassword("river912");
        this.testRepository.save(userByDni);
        User findUserByDni = this.testRepository.findByDni("23200200").get();
        assertEquals(findUserByDni.getDni(), "23200200");
        assertEquals(findUserByDni.getFirstName(), "Martin");
        assertEquals(findUserByDni.getLastName(), "Demichelis");
        assertEquals(findUserByDni.getPassword(), "river912");
    }

    @Test
    void findByDniTest() {
        User user = new User("43908622", "Martin", "Garabal", "austral");
        assertThrows(NoSuchElementException.class, () -> {
            this.testRepository.findByDni("43908622").get();
        });
        this.testRepository.save(user);
        User userByDni = this.testRepository.findByDni("43908622").get();
        assertEquals(userByDni.getDni(), "43908622");
    }

    @Test
    void findAllEmployees() {
    }

    @Test
    void findAllOwners() {
    }
}
