package app.service;

import app.model.User;
import app.model.form.UserEditForm;
import app.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
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

    public void addNewEmployee(@RequestBody @NotNull User user) {
        //check password is 4 digit pin
        user.setOwner(false);
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

    public void deleteEmployee(@NotNull Long userId) {
        final Optional<User> userById = this.userRepository.findById(userId);
        if (userById.isPresent()) {
            if (!(userById.get().isOwner())) {
                this.userRepository.deleteById(userId);
            } else {
                throw new IllegalStateException("user " + userId + " is not employee");
            }
        } else {
            throw new NoSuchElementException("user " + userId + " not found");
        }
    }

    public void modifyOwner(@NotNull Long userId, @NotNull UserEditForm userEditForm) {
        final Optional<User> userById = this.userRepository.findById(userId);
        if (userById.isPresent()) {
            if ((userById.get().isOwner())) {
                this.userRepository.updateUserById(userId, userEditForm.getDni(), userEditForm.getFirstName(), userEditForm.getLastName(), userEditForm.getPassword());

            } else {
                throw new IllegalStateException("user " + userId + " is not owner");
            }
        } else {
            throw new NoSuchElementException("user " + userId + " not found");
        }
    }

    public void modifyEmployee(@NotNull Long userId, @NotNull UserEditForm userEditForm) {
        final Optional<User> userById = this.userRepository.findById(userId);
        if (userById.isPresent()) {
            if (!(userById.get().isOwner())) {
                this.userRepository.updateUserById(userId, userEditForm.getDni(), userEditForm.getFirstName(), userEditForm.getLastName(), userEditForm.getPassword());
            } else {
                throw new IllegalStateException("user " + userId + " is not employee");
            }
        } else {
            throw new NoSuchElementException("user " + userId + " not found");
        }
    }
}
