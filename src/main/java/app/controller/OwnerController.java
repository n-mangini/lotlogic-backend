package app.controller;

import app.model.User;
import app.model.form.ParkingAddForm;
import app.model.form.ParkingEditForm;
import app.model.form.UserEditForm;
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
        return new ResponseEntity<>("employee " + user.getUserId() + " created", HttpStatus.CREATED);
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
        return new ResponseEntity<>("parking " + parking.getAddress() + " created and assigned to user " + parking.getDni(), HttpStatus.CREATED);
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

    //TODO owner should get() from specific parking
    @GetMapping(path = "panel-employee")
    public List<Object> getAllEmployees() {
        return this.userService.getAllEmployees();
    }

    @GetMapping(path = "panel-owner")
    public List<Object> getAllOwners() {
        return this.userService.getAllOwners();
    }
}
