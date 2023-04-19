package app.service;

import app.model.Parking;
import app.model.Reservation;
import app.model.form.ReservationAddForm;
import app.repository.ParkingRepository;
import app.repository.ReservationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ParkingRepository parkingRepository;

    public ReservationService(ReservationRepository reservationRepository, ParkingRepository parkingRepository) {
        this.reservationRepository = reservationRepository;
        this.parkingRepository = parkingRepository;
    }

    public void saveReservation(ReservationAddForm reservationAddForm) {
        if (reservationAddForm.getParkingId() == null)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "reservation should be assigned to parking");
        Reservation reservationToSave = new Reservation(reservationAddForm.getEntryDate(), reservationAddForm.getExitDate(), reservationAddForm.getCarPlate(), reservationAddForm.getCarModel(), reservationAddForm.getCarType());
        this.reservationRepository.save(reservationToSave);
        assignReservationToParking(reservationAddForm.getParkingId(), reservationToSave);
    }

    private void assignReservationToParking(Long parkingId, Reservation reservationToSave) {
        final Optional<Parking> parkingOptional = this.parkingRepository.findByParkingId(parkingId);
        if (parkingOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + parkingId + " not found");
        } else {
            this.reservationRepository.save(reservationToSave);
        }
    }
}
