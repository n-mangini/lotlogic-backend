package app.controller;

import app.model.Parking;
import app.model.User;
import app.model.dto.ParkingAddForm;
import app.model.dto.ParkingEditForm;
import app.model.dto.UserEditForm;
import app.service.ParkingService;
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

    @Autowired
    public OwnerController(final UserService userService, ParkingService parkingService) {
        this.userService = userService;
        this.parkingService = parkingService;
    }

    @PostMapping(path = "/add-employee", consumes = {"application/json"})
    public ResponseEntity<?> createEmployee(@RequestBody final User user) {
        this.userService.saveEmployee(user);
        return new ResponseEntity<>("employee " + user.getDni() + " created", HttpStatus.CREATED);
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
    public ResponseEntity<?> createParking(@RequestBody final ParkingAddForm parking) {
        this.parkingService.saveParking(parking);
        return new ResponseEntity<>("parking " + parking.address() + " created and assigned to user " + parking.dni(), HttpStatus.CREATED);
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

    @GetMapping(path = "panel-parkings/{userId}")
    public List<Parking> getAllParkings(@PathVariable final Long userId) {
        return this.parkingService.getAllParkings(userId);
    }

    //TODO get all employees from his parkings
    @GetMapping(path = "panel-employees")
    public List<User> getAllEmployees() {
        return this.userService.getAllEmployees();
    }
}
