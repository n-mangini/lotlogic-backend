package app.controller;

import app.model.Parking;
import app.model.User;
import app.model.dto.EmployeeAddForm;
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
    public ResponseEntity<?> createEmployee(@RequestBody final EmployeeAddForm employee) {
        this.userService.saveEmployee(employee);
        return new ResponseEntity<>("employee " + employee.user().getDni() + " created", HttpStatus.CREATED);
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

    //TODO get all employees from his parkings
    @GetMapping(path = "panel-employees")
    public List<User> getAllEmployees() {
        return this.userService.getAllEmployees();
    }
}
