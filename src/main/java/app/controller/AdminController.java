package app.controller;

import app.model.Parking;
import app.model.Reservation;
import app.model.User;
import app.model.dto.UserEditForm;
import app.service.ParkingService;
import app.service.ReservationService;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/user/admin")
public class AdminController {
    private final UserService userService;
    private final ParkingService parkingService;
    private final ReservationService reservationService;

    @Autowired
    public AdminController(final UserService userService, ParkingService parkingService, ReservationService reservationService) {
        this.userService = userService;
        this.parkingService = parkingService;
        this.reservationService = reservationService;
    }

    @PostMapping(path = "/add-owner", consumes = {"application/json"})
    public ResponseEntity<?> createOwner(@RequestBody final User user) {
        this.userService.saveOwner(user);
        return new ResponseEntity<>("owner " + user.getDni() + " created", HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/delete-owner/{userId}")
    public ResponseEntity<?> deleteOwner(@PathVariable final Long userId) {
        this.userService.deleteOwner(userId);
        return new ResponseEntity<>("owner " + userId + " deleted", HttpStatus.OK);
    }

    @PutMapping(path = "/update-owner/{userId}", consumes = {"application/json"})
    public ResponseEntity<?> modifyOwner(@PathVariable final Long userId, @RequestBody final UserEditForm user) {
        this.userService.updateOwner(userId, user);
        return new ResponseEntity<>("owner " + userId + " updated", HttpStatus.OK);
    }

    @GetMapping(path = "panel-employees")
    public List<User> getAllEmployees() {
        return this.userService.getAllEmployees();
    }

    @GetMapping(path = "panel-owners")
    public List<User> getAllOwners() {
        return this.userService.getAllOwners();
    }

    @GetMapping(path = "panel-parkings")
    public List<Parking> getAllParkings() {
        return this.parkingService.getAllParkings();
    }

    @GetMapping(path = "panel-reservations")
    public List<Reservation> getAllReservations(){
        return this.reservationService.findAllReservations();
    }
}
