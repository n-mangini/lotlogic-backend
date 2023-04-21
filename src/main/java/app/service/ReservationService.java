package app.service;

import app.model.Parking;
import app.model.Reservation;
import app.model.User;
import app.model.dto.ReservationAddForm;
import app.repository.ParkingRepository;
import app.repository.ReservationRepository;
import app.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ParkingRepository parkingRepository;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository, ParkingRepository parkingRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.parkingRepository = parkingRepository;
        this.userRepository = userRepository;
    }

    public void saveReservation(ReservationAddForm reservationAddForm) {
        if (reservationAddForm.parkingId() == null || reservationAddForm.dni() == null)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "reservation should be assigned to parking and user");
        Reservation reservationToSave = new Reservation(reservationAddForm.entryDate(), reservationAddForm.exitDate(), reservationAddForm.carPlate(), reservationAddForm.carModel(), reservationAddForm.carType());
        this.reservationRepository.save(reservationToSave);
        assignReservationToParking(reservationAddForm.parkingId(), reservationToSave);
        assignReservationToEmployee(reservationAddForm.dni(), reservationToSave);
    }

    private void assignReservationToParking(Long parkingId, Reservation reservationToSave) {
        final Optional<Parking> parkingOptional = this.parkingRepository.findById(parkingId);
        if (parkingOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "parking " + parkingId + " not found");
        } else {
            this.reservationRepository.save(reservationToSave);
        }
    }

    private void assignReservationToEmployee(String dni, Reservation reservationToSave){
        final Optional<User> userOptional = this.userRepository.findByDni(dni);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + dni + " not found");
        } else {

        }
    }
}
