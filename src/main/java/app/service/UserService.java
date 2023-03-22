package app.service;

import app.model.User;
import app.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addNewOwner(@RequestBody @NotNull User user) {
        checkOwnerWithSameDniExists(user);
        checkOwnerPassword(user.getPassword());
        this.userRepository.save(user);
    }

    public void deleteOwner(@NotNull Long userId) {
        checkOwnerExistsById(userId);
        this.userRepository.deleteById(userId);
    }

    public void modifyOwner(String dni, String firstName, String lastName, String password, @NotNull Long userId) {
        checkOwnerExistsById(userId);
        this.userRepository.updateUserById(dni, firstName, lastName, password, userId);
    }

    private void checkOwnerExistsById(Long userId) {
        if (!this.userRepository.existsById(userId))
            throw new IllegalStateException("user " + userId + " not found");
    }

    private void checkOwnerWithSameDniExists(final User user) {
        Optional<User> userOptional = this.userRepository.findUserByDni(user.getDni());
        if (userOptional.isPresent())
            throw new IllegalStateException("user with dni: " + userOptional.get().getDni() + " already exists");
    }

    private void checkOwnerPassword(final String password) {
        if (password.length() < 4) throw new IllegalStateException("password is too short");
    }
}
