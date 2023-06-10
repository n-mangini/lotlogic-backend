package app.service;

import app.model.Parking;
import app.model.User;
import app.model.UserRole;
import app.model.dto.EmployeeAddForm;
import app.model.dto.UserEditForm;
import app.model.projection.UserProjection;
import app.model.dto.UserLoginForm;
import app.repository.ParkingRepository;
import app.repository.UserRepository;
import app.security.config.JwtService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Component
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ParkingService parkingService;
    private final ParkingRepository parkingRepository;

    public UserService(UserRepository userRepository, JwtService jwtService, ParkingService parkingService, ParkingRepository parkingRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.parkingService = parkingService;
        this.parkingRepository = parkingRepository;
    }

    public String loginUser(@RequestBody @NotNull UserLoginForm user) {
        final Optional<User> userDni = this.userRepository.findByDni(user.dni());
        if (userDni.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + user.dni() + " not found");
        }
        final Optional<User> userData = this.userRepository.findUserByDniAndPassword(user.dni(), user.password());
        if (userData.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "user or password invalid");
        }
        String token = this.jwtService.generateToken(user, getUserRoleById(user.dni()));
        this.jwtService.tokens.put(token, null);
        return token;
    }

    //TODO add possibility to have employee and owner with same dni then fix test exception msg

    public void saveOwner(@RequestBody @NotNull User user) {
        final Optional<User> userByDni = this.userRepository.findByDni(user.getDni());
        if (userByDni.isEmpty()) {
            if (user.getPassword().length() <= 4)
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "password is too short");
            user.setRole(UserRole.OWNER);
            this.userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "owner with DNI " + userByDni.get().getDni() + " already exists");
        }
    }
    //TODO add possibility to have employee and owner with same dni then fix test exception msg

    public void saveEmployee(@RequestBody @NotNull EmployeeAddForm employeeAddForm) {
        final Optional<User> userByDni = this.userRepository.findByDni(employeeAddForm.user().getDni());
        if (userByDni.isEmpty()) {
            if (employeeAddForm.user().getPassword().length() > 4)
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "PIN must be 4 digit");
            employeeAddForm.user().setRole(UserRole.EMPLOYEE);
            Parking parking = this.parkingService.getParkingById(employeeAddForm.parkingId());
            parking.setEmployee(employeeAddForm.user());
            this.parkingRepository.save(parking);
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
                userById.setDni(userEditForm.dni());
                userById.setFirstName(userEditForm.firstName());
                userById.setLastName(userEditForm.lastName());
                userById.setPassword(userEditForm.password());
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
                userById.setDni(userEditForm.dni());
                userById.setFirstName(userEditForm.firstName());
                userById.setLastName(userEditForm.lastName());
                userById.setPassword(userEditForm.password());
                this.userRepository.save(userById);
            } else {
                throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "user " + userId + " is not employee");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + userId + " not found");
        }
    }

    private String getUserRoleById(String dni) {
        return this.userRepository.findRoleByDni(dni);
    }

    public User getUserById(Long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + id + " not found");
        }
        return userOptional.get();
    }

    public User getUserByDni(String dni) {
        Optional<User> userOptional = this.userRepository.findByDni(dni);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + dni + " not found");
        }
        return userOptional.get();
    }

    public List<User> getAllEmployees() {
        return this.userRepository.findAllEmployees();
    }

    public List<User> getAllEmployees(String ownerDni) {
        Optional<User> userOptional = this.userRepository.findByDni(ownerDni);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + ownerDni + " not found");
        }
        return userOptional.get().getParkings().stream()
                .map(Parking::getEmployee)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<UserProjection> getAllOwners() {
        return this.userRepository.findAllByActiveAndRole(true, UserRole.OWNER);
    }
}
