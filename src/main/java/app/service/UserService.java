package app.service;

import app.model.User;
import app.model.UserRole;
import app.model.form.UserEditForm;
import app.model.form.UserLoginForm;
import app.repository.UserRepository;
import app.security.config.JwtService;
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
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public String loginUser(@RequestBody @NotNull UserLoginForm user) {
        final Optional<User> userData = this.userRepository.findUserByDniAndPassword(user.getDni(), user.getPassword());
        if (userData.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + user.getDni() + " not found");
        }
        return this.jwtService.generateToken(user, getUserRoleById(user.getDni()));
    }

    //TODO add possibility to have employee and owner with same dni then fix test exception msg

    public void saveOwner(@RequestBody @NotNull User user) {
        final Optional<User> userByDni = this.userRepository.findByDni(user.getDni());
        if (userByDni.isEmpty() || !userByDni.get().isActive()) {
            if (user.getPassword().length() <= 4)
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "password is too short");
            user.setRole(UserRole.OWNER);
            this.userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "owner with DNI " + userByDni.get().getDni() + " already exists");
        }
    }
    //TODO add possibility to have employee and owner with same dni then fix test exception msg

    public void saveEmployee(@RequestBody @NotNull User user) {
        final Optional<User> userByDni = this.userRepository.findByDni(user.getDni());
        if (userByDni.isEmpty() || !userByDni.get().isActive()) {
            if (user.getPassword().length() > 4)
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "PIN must be 4 digit");
            user.setRole(UserRole.EMPLOYEE);
            this.userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "employee with DNI " + userByDni.get().getDni() + " already exists");
        }
    }

    public void deleteOwner(@NotNull Long userId) {
        final Optional<User> userOptional = this.userRepository.findById(userId);
        if (userOptional.isPresent() && userOptional.get().isActive()) {
            User userById = userOptional.get();
            if (userById.getRole().equals(UserRole.OWNER)) {
                userById.setActive(false);
                this.userRepository.save(userById);
            } else {
                throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "user " + userId + " is not owner");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + userId + " not found");
        }
    }

    public void deleteEmployee(@NotNull Long userId) {
        final Optional<User> userOptional = this.userRepository.findById(userId);
        if (userOptional.isPresent() && userOptional.get().isActive()) {
            User userById = userOptional.get();
            if (userById.getRole().equals(UserRole.EMPLOYEE)) {
                userById.setActive(false);
                this.userRepository.save(userById);
            } else {
                throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "user " + userId + " is not employee");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + userId + " not found");
        }
    }

    public void updateOwner(@NotNull Long userId, @NotNull UserEditForm userEditForm) {
        final Optional<User> findUserById = this.userRepository.findById(userId);
        if (findUserById.isPresent()) {
            User userById = findUserById.get();
            if ((userById.getRole().equals(UserRole.OWNER))) {
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

    public void updateEmployee(@NotNull Long userId, @NotNull UserEditForm userEditForm) {
        final Optional<User> findUserById = this.userRepository.findById(userId);
        if (findUserById.isPresent()) {
            User userById = findUserById.get();
            if (userById.getRole().equals(UserRole.EMPLOYEE)) {
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

    private String getUserRoleById(String dni) {
        return this.userRepository.findRoleById(dni);
    }

    public User getUserByDni(String dni) {
        Optional<User> userOptional = this.userRepository.findByDni(dni);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + dni + " not found");
        }
        return userOptional.get();
    }

    public List<Object> getAllEmployees() {
        return this.userRepository.findAllEmployees();
    }

    public List<Object> getAllOwners() {
        return this.userRepository.findAllOwners();
    }
}
