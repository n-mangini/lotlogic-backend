package app.controller;

import app.model.Parking;
import app.model.Reservation;
import app.model.User;
import app.model.dto.ParkingAddForm;
import app.model.dto.ParkingEditForm;
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
@RequestMapping(path = "/api/user/owner")
public class OwnerController {
    private final UserService userService;
    private final ParkingService parkingService;
    private final ReservationService reservationService;

    @Autowired
    public OwnerController(final UserService userService, ParkingService parkingService, ReservationService reservationService) {
        this.userService = userService;
        this.parkingService = parkingService;
        this.reservationService = reservationService;
    }

    @PostMapping(path = "/add-employee/{parkingId}", consumes = {"application/json"})
    public ResponseEntity<?> createEmployee(@PathVariable Long parkingId, @RequestBody final User employee) {
        this.userService.saveEmployee(employee, parkingId);
        return new ResponseEntity<>("employee " + employee.getDni() + " created", HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/delete-employee/{userId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable final Long userId) {
        this.userService.deleteEmployee(userId);
        return new ResponseEntity<>("employee " + userId + " deleted", HttpStatus.OK);
    }

    @PutMapping(path = "/update-employee/{userId}", consumes = {"application/json"})
    public ResponseEntity<?> modifyEmployee(@PathVariable final Long userId, @RequestBody final UserEditForm user) {
        this.userService.updateEmployee(userId, user);
        return new ResponseEntity<>("employee " + userId + " updated", HttpStatus.OK);
    }

    @PostMapping(path = "/add-parking", consumes = {"application/json"})
    public ResponseEntity<String> createParking(@RequestBody final ParkingAddForm parking) {
        Long parkingId = this.parkingService.saveParking(parking);
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingId.toString());
    }

    @DeleteMapping(path = "/delete-parking/{parkingId}")
    public ResponseEntity<?> deleteParking(@PathVariable final Long parkingId) {
        this.parkingService.deleteParking(parkingId);
        return new ResponseEntity<>("parking " + parkingId + " deleted", HttpStatus.OK);
    }

    @PutMapping(path = "/update-parking/{parkingId}", consumes = {"application/json"})
    public ResponseEntity<?> modifyParking(@PathVariable final Long parkingId, @RequestBody final ParkingEditForm parking) {
        this.parkingService.updateParking(parkingId, parking);
        return new ResponseEntity<>("parking " + parkingId + " updated", HttpStatus.OK);
    }

    @GetMapping(path = "panel-parkings/{ownerDni}")
    public List<Parking> getAllParkings(@PathVariable final String ownerDni) {
        return this.parkingService.getAllParkings(ownerDni);
    }

    @GetMapping(path = "panel-reservations/{ownerDni}")
    public List<Reservation> getAllReservations(@PathVariable final String ownerDni){
        return this.reservationService.findAllReservations(ownerDni);
    }

    @GetMapping(path = "panel-employees/{ownerDni}")
    public List<User> getAllEmployees(@PathVariable final String ownerDni) {
        return this.userService.getAllEmployees(ownerDni);
    }

    @GetMapping(path = "employee-info/{employeeDni}")
    public User getEmployee(@PathVariable final String employeeDni){
        return this.userService.getUserByDni(employeeDni);
    }

    @GetMapping(path = "get-parking/{parkingId}")
    public Parking getEmployee(@PathVariable final Long parkingId){
        return this.parkingService.getParkingById(parkingId);
    }
}
