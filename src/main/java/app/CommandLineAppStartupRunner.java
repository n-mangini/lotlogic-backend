package app;

import app.model.Floor;
import app.model.Parking;
import app.model.User;
import app.model.UserRole;
import app.model.form.ParkingAddForm;
import app.repository.FloorRepository;
import app.repository.UserRepository;
import app.service.ParkingService;
import app.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class
CommandLineAppStartupRunner implements CommandLineRunner {
    final UserRepository userRepository;
    final FloorRepository floorRepository;
    final UserService userService;
    final ParkingService parkingService;

    public CommandLineAppStartupRunner(UserRepository userRepository, FloorRepository floorRepository, UserService userService, ParkingService parkingService) {
        this.userRepository = userRepository;
        this.floorRepository = floorRepository;
        this.userService = userService;
        this.parkingService = parkingService;
    }

    @Override
    public void run(String... args) {
        //user
        User admin = new User("ADMIN", "ADMIN", "ADMIN", "123456");
        admin.setRole(UserRole.ADMIN);
        User owner1 = new User("20000000", "Martin", "Garabal", "123456");
        owner1.setRole(UserRole.OWNER);
        User employee1 = new User("40000000", "Miguel", "Granados", "1234");
        employee1.setRole(UserRole.EMPLOYEE);
        this.userRepository.save(admin);
        this.userRepository.save(owner1);
        this.userRepository.save(employee1);
    }
}
