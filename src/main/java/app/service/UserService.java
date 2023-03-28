package app.service;

import app.model.User;
import app.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Component
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addNewOwner(@RequestBody @NotNull User user) {
        if (user.getPassword().length() < 4) throw new IllegalStateException("password is too short");
        user.setOwner(true);
        this.userRepository.save(user);
    }

    public void deleteOwner(@NotNull Long userId) {
        final Optional<User> userById = this.userRepository.findById(userId);
        if (userById.isPresent()) {
            if (userById.get().isOwner()) {
                this.userRepository.deleteById(userId);
            } else {
                throw new IllegalStateException("user " + userId + " is not owner");
            }
        } else {
            throw new NoSuchElementException("user " + userId + " not found");
        }
    }

    public void modifyOwner(String dni, String firstName, String lastName, String password, @NotNull Long userId) {
        final Optional<User> userById = this.userRepository.findById(userId);
        if (userById.isPresent()) {
            this.userRepository.updateUserById(dni, firstName, lastName, password, userId);
        } else {
            throw new NoSuchElementException("user " + userId + " not found");
        }
    }
}
