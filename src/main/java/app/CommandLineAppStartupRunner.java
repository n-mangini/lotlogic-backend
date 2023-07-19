package app;

import app.model.*;
import app.repository.*;
import app.service.ParkingService;
import app.service.ReservationService;
import app.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class
CommandLineAppStartupRunner implements CommandLineRunner {
    final FeeRepository feeRepository;
    final FloorRepository floorRepository;
    final ParkingRepository parkingRepository;
    final ReservationRepository reservationRepository;
    final UserRepository userRepository;


    public CommandLineAppStartupRunner(FeeRepository feeRepository, UserRepository userRepository, FloorRepository floorRepository, UserService userService, ParkingService parkingService, ParkingRepository parkingRepository, ReservationService reservationService, ReservationRepository reservationRepository) {
        this.feeRepository = feeRepository;
        this.userRepository = userRepository;
        this.floorRepository = floorRepository;
        this.parkingRepository = parkingRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void run(String... args) {
        //USER
        User ADMIN = new User("ADMIN", "ADMIN", "ADMIN", "123456");
        ADMIN.setRole(UserRole.ADMIN);

        //OWNER
        User owner1 = new User("37096854", "Martin", "Garabal", "123456");
        owner1.setRole(UserRole.OWNER);
        User owner2 = new User("947365", "Guillermo", "Francella", "gfd74k");
        owner2.setRole(UserRole.OWNER);
        User owner3 = new User("37096854", "Anya", "Taylor-Joy", "k29fCJ");
        owner3.setRole(UserRole.OWNER);
        User owner4 = new User("12345678", "Lali", "Espósito", "d38sKa");
        owner4.setRole(UserRole.OWNER);
        User owner5 = new User("98765432", "Ricardo", "Darín", "zK14dj");
        owner5.setRole(UserRole.OWNER);
        User owner6 = new User("54321098", "Diego", "Peretti", "p72HkW");
        owner6.setRole(UserRole.OWNER);
        User owner7 = new User("45678901", "Valentina", "Zenere", "t92XrP");
        owner7.setRole(UserRole.OWNER);

        //EMPLOYEE
        User employee1 = new User("937452", "Adán", "Bahl", "1234");
        employee1.setRole(UserRole.EMPLOYEE);
        User employee2 = new User("683592", "Juan Carlos", "Alcoba", "5678");
        employee2.setRole(UserRole.EMPLOYEE);
        User employee3 = new User("205618", "Olegario Antonio", "Becerra", "9012");
        employee3.setRole(UserRole.EMPLOYEE);

        this.userRepository.save(ADMIN);
        this.userRepository.save(owner1);
        this.userRepository.save(owner2);
        this.userRepository.save(owner3);
        this.userRepository.save(owner4);
        this.userRepository.save(owner5);
        this.userRepository.save(owner6);
        this.userRepository.save(owner7);

        this.userRepository.save(employee1);
        this.userRepository.save(employee2);
        this.userRepository.save(employee3);

        //PARKINGS
        List<Floor> floors1 = new ArrayList<>(Arrays.asList(new Floor(70), new Floor(60), new Floor(60)));
        List<Fee> fees1 = new ArrayList<>(Arrays.asList(new Fee("AUTO", 500), new Fee("CAMIONETA", 800), new Fee("MOTO", 100)));
        owner1.getParkings().add(new Parking("Av Corrientes 3247", floors1, fees1));
        owner1.getParkings().get(0).setEmployee(employee1);
        this.userRepository.save(owner1);

        List<Floor> floors2 = new ArrayList<>(Arrays.asList(new Floor(30), new Floor(31), new Floor(29)));
        List<Fee> fees2 = new ArrayList<>(Arrays.asList(new Fee("AUTO", 500), new Fee("CAMIONETA", 500), new Fee("MOTO", 100)));
        owner2.getParkings().add(new Parking("Jeronimo Salguero 3172", floors2, fees2));
        owner2.getParkings().get(0).setEmployee(employee2);
        this.userRepository.save(owner2);

        List<Floor> floors3 = new ArrayList<>(Arrays.asList(new Floor(100), new Floor(100), new Floor(90)));
        List<Fee> fees3 = new ArrayList<>(Arrays.asList(new Fee("AUTO", 520), new Fee("CAMIONETA", 600), new Fee("MOTO", 220)));
        owner3.getParkings().add(new Parking("Av Figueroa Alcorta 3102", floors3, fees3));
        owner3.getParkings().get(0).setEmployee(employee3);
        this.userRepository.save(owner3);

        //3 autos en el piso 1
        Reservation reservation1 = new Reservation(1, "ABC123", "Tesla Model X", 1L, "2023/07/18 20:00:00", "2023/07/18 23:00:00", "Av Corrientes 3247");
        Reservation reservation2 = new Reservation(1, "JDK823", "Renault Sandero", 1L, "2023/07/18 15:30:00", null, "Av Corrientes 3247");
        Reservation reservation3 = new Reservation(1, "AB839JSI", "Peugeot 208", 1L, "2023/07/18 16:00:00", null, "Av Corrientes 3247");
        //3 camionetas en el piso 2
        Reservation reservation4 = new Reservation(2, "AKD928", "Volkswagen Amarok", 2L, "2023/07/17 13:00:00", "2023/07/17 17:21:00", "Av Corrientes 3247");
        Reservation reservation5 = new Reservation(2, "OUR291", "Toyota Hilux", 2L, "2023/07/18 11:30:00", null, "Av Corrientes 3247");
        Reservation reservation6 = new Reservation(2, "AD020AA", "Dodge RAM", 2L, "2023/07/18 09:00:00", null, "Av Corrientes 3247");

        Parking avCorrientesParking = this.parkingRepository.findById(1L).get();
        avCorrientesParking.setReservations(new ArrayList<>(Arrays.asList(reservation1, reservation2, reservation3, reservation4, reservation5, reservation6)));
        this.parkingRepository.save(avCorrientesParking);


        Reservation reservation7 = new Reservation(4, "A123BCD", "Honda Wave", 6L, "2023/07/18 20:00:00", "2023/07/18 23:00:00", "Jeronimo Salguero 3172");
        Reservation reservation8 = new Reservation(5, "678IZJ", "Tornado", 6L, "2023/07/18 15:30:00", "2023/07/18 23:00:00", "Jeronimo Salguero 3172");
        Reservation reservation9 = new Reservation(5, "960GAO", "Econo C90", 6L, "2023/07/18 16:00:00", null, "Jeronimo Salguero 3172");
        Parking jeronimoSalgueroParking = this.parkingRepository.findById(2L).get();
        jeronimoSalgueroParking.setReservations(new ArrayList<>(Arrays.asList(reservation7, reservation8, reservation9)));
        this.parkingRepository.save(jeronimoSalgueroParking);
    }
}
