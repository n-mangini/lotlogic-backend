package app.service;

import app.model.Parking;
import app.model.Reservation;
import app.model.dto.ReservationAddForm;
import app.model.dto.ReservationEditForm;
import app.repository.ParkingRepository;
import app.repository.ReservationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ParkingRepository parkingRepository;

    public ReservationService(ReservationRepository reservationRepository, ParkingRepository parkingRepository) {
        this.reservationRepository = reservationRepository;
        this.parkingRepository = parkingRepository;
    }

    //TODO exceptions
    public void saveReservation(ReservationAddForm reservationAddForm) {
        Parking parking = this.parkingRepository.findById(reservationAddForm.parkingId()).get();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
        parking.getReservations().add(new Reservation(reservationAddForm.carPlate(), reservationAddForm.carModel(), reservationAddForm.carType(), dtf.format(date), reservationAddForm.exitDate()));
        this.parkingRepository.save(parking);
    }

    public List<Reservation> findAllReservations(Long parkingId) {
        return this.reservationRepository.findAllReservations(parkingId);
    }

    public List<Reservation> findAllReservations() {
        return this.reservationRepository.findAllReservations();
    }
    //TODO
    public void updateReservation(ReservationEditForm reservationEditForm) {
        Optional<Parking> parkingOptional = this.parkingRepository.findById(reservationEditForm.parkingId());
        if (parkingOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "parking " + reservationEditForm.parkingId() + " not found");
        }
        Parking parking = parkingOptional.get();
        Optional<Reservation> reservationOptional = parking.getReservations().stream()
                .filter(reservation -> Objects.equals(reservation.getId(), reservationEditForm.reservationId()))
                .findFirst();
        if (reservationOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "reservation not found");
        }
        Reservation reservation = reservationOptional.get();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
        reservation.setExitDate(dtf.format(date));
        this.reservationRepository.save(reservation);
    }
}
