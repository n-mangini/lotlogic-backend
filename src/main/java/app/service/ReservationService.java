package app.service;

import app.model.*;
import app.model.dto.ReservationAddForm;
import app.model.dto.ReservationEditForm;
import app.repository.FloorRepository;
import app.repository.ParkingRepository;
import app.repository.ReservationRepository;
import app.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ParkingRepository parkingRepository;
    private final FloorRepository floorRepository;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository, ParkingRepository parkingRepository, FloorRepository floorRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.parkingRepository = parkingRepository;
        this.floorRepository = floorRepository;
        this.userRepository = userRepository;
    }

    public void saveReservation(ReservationAddForm reservationAddForm) {
        Optional<Parking> parkingOptional = this.parkingRepository.findById(reservationAddForm.parkingId());
        if (parkingOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "parking " + reservationAddForm.parkingId() + " not found");
        } else {
            Parking parking = parkingOptional.get();
            //throw if parking floor is disabled
            if (!this.floorRepository.findById(reservationAddForm.floor()).get().isEnabled()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "floor " + reservationAddForm.floor() + " is not enabled");
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime date = LocalDateTime.now();
            parking.getReservations().add(new Reservation(reservationAddForm.floor().intValue(), reservationAddForm.vehiclePlate(), reservationAddForm.vehicleModel(), reservationAddForm.fee(), dtf.format(date), null));
            this.parkingRepository.save(parking);
        }
    }


    //TODO

    public void updateReservation(ReservationEditForm reservationEditForm, Long reservationId) {
        Optional<Parking> parkingOptional = this.parkingRepository.findById(reservationEditForm.parkingId());
        if (parkingOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "parking " + reservationEditForm.parkingId() + " not found");
        }
        Parking parking = parkingOptional.get();
        Optional<Reservation> reservationOptional = parking.getReservations().stream()
                .filter(reservation -> Objects.equals(reservation.getId(), reservationId))
                .findFirst();
        if (reservationOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "reservation not found");
        }
        Reservation reservation = reservationOptional.get();
        if (reservation.getExitDate() != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "reservation already ended");
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
        reservation.setExitDate(dtf.format(date));
        this.reservationRepository.save(reservation);
    }

    public List<Reservation> findAllReservations() {
        return this.reservationRepository.findAllReservations();
    }

    public List<Reservation> findAllReservations(Long parkingId) {
        return this.reservationRepository.findAllReservations(parkingId);
    }

    public List<Reservation> findAllReservations(String ownerDni) {
        final Optional<User> userOptional = this.userRepository.findByDni(ownerDni);
        if (userOptional.isEmpty()){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + ownerDni + " not found");
        }
        return userOptional.get().getParkings().stream()
                .flatMap(parking -> parking.getReservations().stream())
                .collect(Collectors.toList());
    }

    public List<Reservation> findAllCurrentReservations(Long parkingId) {
        return this.reservationRepository.findAllCurrentReservations(parkingId);    }

    public List<Fee> findAllFees(Long parkingId) {
        return this.parkingRepository.findAllFees(parkingId);
    }

    public List<Floor> findAllFloors(Long parkingId) {
        return this.parkingRepository.findAllFloors(parkingId);
    }
}
