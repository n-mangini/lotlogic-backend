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

    public void saveReservation(ReservationAddForm reservationAddForm) {
        Optional<Parking> parkingOptional = this.parkingRepository.findById(reservationAddForm.parkingId());
        if (parkingOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "parking " + reservationAddForm.parkingId() + " not found");
        } else {
            Parking parking = parkingOptional.get();
            //throw if parking floor is disabled
            if (!parking.getFloors().get(reservationAddForm.floor() + 1).isEnabled()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "floor " + reservationAddForm.floor() + " is not enabled");
            }
            //throws exception when tries to create reservation(check in) in floor which doesn't exist in parking
            if (parking.getFloors().size() < reservationAddForm.floor()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "parking doesnt have floor" + reservationAddForm.floor());
            }
            //throws when amount of reservations in that floor [cars] are >= than slots number
            if (findAllReservations(reservationAddForm.parkingId()).size() >= parking.getFloors().get(reservationAddForm.floor()).getSlotsNumber()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "floor " + reservationAddForm.floor() + " is full");
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime date = LocalDateTime.now();
            parking.getReservations().add(new Reservation(reservationAddForm.floor(), reservationAddForm.vehiclePlate(), reservationAddForm.vehicleModel(), reservationAddForm.vehicleType(), dtf.format(date), null));
            this.parkingRepository.save(parking);
        }
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
