package app.controller;

import app.model.form.ReservationAddForm;
import app.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/user/employee")
public class EmployeeController {
    private final ReservationService reservationService;

    public EmployeeController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping(path = "/add-reservation", consumes = {"application/json"})
    public ResponseEntity<?> createReservation(@RequestBody final ReservationAddForm reservationAddForm) {
        this.reservationService.saveReservation(reservationAddForm);
        return new ResponseEntity<>("reservation created", HttpStatus.CREATED);
    }
}
