package app.controller;

import app.model.Reservation;
import app.model.dto.ReservationAddForm;
import app.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(path = "panel-reservations/{parkingId}")
    public List<Reservation> getAllReservations(@PathVariable Long parkingId){
        return this.reservationService.findAllReservations(parkingId);
    }
}
