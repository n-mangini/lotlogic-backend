package app.service;

import app.model.User;
import app.model.UserRole;
import app.model.form.UserEditForm;
import app.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Component
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addNewOwner(@RequestBody @NotNull User user) {
        final Optional<User> userByDni = this.userRepository.findByDni(user.getDni());
        if (userByDni.isEmpty()) {
            if (user.getPassword().length() < 4)
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "password is too short");
            user.setRole(UserRole.OWNER);
            this.userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "owner with DNI " + userByDni.get().getDni() + " already exists");
        }
    }

    public void addNewEmployee(@RequestBody @NotNull User user) {
        final Optional<User> userByDni = this.userRepository.findByDni(user.getDni());
        if (userByDni.isEmpty()) {
            if (user.getPassword().length() > 4)
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "PIN must be 4 digit");
            user.setRole(UserRole.EMPLOYEE);
            this.userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "employee with DNI " + userByDni.get().getDni() + " already exists");
        }
    }

    public void deleteOwner(@NotNull Long userId) {
        final Optional<User> userById = this.userRepository.findById(userId);
        if (userById.isPresent()) {
            if (userById.get().isOwner()) {
                this.userRepository.deleteById(userId);
            } else {
                throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "user " + userId + " is not owner");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + userId + " not found");
        }
    }

    public void deleteEmployee(@NotNull Long userId) {
        final Optional<User> userById = this.userRepository.findById(userId);
        if (userById.isPresent()) {
            if (!(userById.get().isOwner())) {
                this.userRepository.deleteById(userId);
            } else {
                throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "user " + userId + " is not employee");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + userId + " not found");
        }
    }

    public void modifyOwner(@NotNull Long userId, @NotNull UserEditForm userEditForm) {
        final Optional<User> findUserById = this.userRepository.findById(userId);
        if (findUserById.isPresent()) {
            User userById = findUserById.get();
            if ((userById.isOwner())) {
                userById.setDni(userEditForm.getDni());
                userById.setFirstName(userEditForm.getFirstName());
                userById.setLastName(userEditForm.getLastName());
                userById.setPassword(userEditForm.getPassword());
                this.userRepository.save(userById);
            } else {
                throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "user " + userId + " is not owner");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + userId + " not found");
        }
    }

    public void modifyEmployee(@NotNull Long userId, @NotNull UserEditForm userEditForm) {
        final Optional<User> findUserById = this.userRepository.findById(userId);
        if (findUserById.isPresent()) {
            User userById = findUserById.get();
            if (!(userById.isOwner())) {
                userById.setDni(userEditForm.getDni());
                userById.setFirstName(userEditForm.getFirstName());
                userById.setLastName(userEditForm.getLastName());
                userById.setPassword(userEditForm.getPassword());
                this.userRepository.save(userById);
            } else {
                throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "user " + userId + " is not employee");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + userId + " not found");
        }
    }

    public List<Object> findAllEmployees() {
        return this.userRepository.findAllEmployees();
    }

    public List<Object> findAllOwners() {
        return this.userRepository.findAllOwners();
    }
}
