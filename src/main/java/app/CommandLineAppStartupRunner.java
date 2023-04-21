package app;

import app.model.*;
import app.repository.FloorRepository;
import app.repository.UserRepository;
import app.service.ParkingService;
import app.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        List<Floor> floors = new ArrayList<>(Arrays.asList(new Floor(50), new Floor(100), new Floor(75)));
        List<Fee> fees = new ArrayList<>(Arrays.asList(new Fee(200, "CAR"), new Fee(100, "TRUCK"), new Fee(100, "MOTORCYCLE")));
        owner1.getParkings().add(new Parking("Antezana 247, CABA", floors, fees));
        this.userRepository.save(owner1);
    }
}
