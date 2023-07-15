package app.service;

import app.model.*;
import app.model.dto.ReservationAddForm;
import app.model.dto.ReservationEditForm;
import app.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ParkingRepository parkingRepository;
    private final FloorRepository floorRepository;
    private final UserRepository userRepository;
    private final FeeRepository feeRepository;

    public ReservationService(ReservationRepository reservationRepository, ParkingRepository parkingRepository, FloorRepository floorRepository, UserRepository userRepository, FeeRepository feeRepository) {
        this.reservationRepository = reservationRepository;
        this.parkingRepository = parkingRepository;
        this.floorRepository = floorRepository;
        this.userRepository = userRepository;
        this.feeRepository = feeRepository;
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

            int reservationsPerFloor = this.reservationRepository.findAllCurrentReservationsPerFloor(reservationAddForm.parkingId(), reservationAddForm.floor()).size();
            int floorSlots = this.floorRepository.findById(reservationAddForm.floor()).get().getSlotsNumber();
            if (reservationsPerFloor >= floorSlots){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "floor " + reservationAddForm.floor() + " is full");
            }

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime date = LocalDateTime.now();
            parking.getReservations().add(new Reservation(reservationAddForm.floor().intValue(), reservationAddForm.vehiclePlate(), reservationAddForm.vehicleModel(), reservationAddForm.fee(), dtf.format(date), null, parking.getAddress()));
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
        if (reservation.getExitDate() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "reservation already ended");
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
        reservation.setExitDate(dtf.format(date));
        this.reservationRepository.save(reservation);
    }

    //when exit car is set //just set reservation price
    public Reservation createTicket(Long reservationId) {
        Optional<Reservation> reservationOptional = this.reservationRepository.findById(reservationId);

        if (reservationOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "reservation not found");
        }
        Reservation reservation = reservationOptional.get();
        if (reservation.getExitDate() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "reservation already ended");
        }
        Double price = calculateReservationPrice(reservationId);
        reservation.setAmount(price);
        this.reservationRepository.save(reservation);
        return reservation;
    }

    public Double calculateReservationPrice(Long reservationId) {
        Optional<Reservation> reservationOptional = this.reservationRepository.findById(reservationId);
        if (reservationOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "reservation " + reservationId + " not found");
        } else {
            Reservation reservation = reservationOptional.get();
            String entryDate = reservation.getEntryDate();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

            LocalDateTime dateTime1 = LocalDateTime.parse(entryDate, dtf);
            Duration duration = Duration.between(dateTime1, LocalDateTime.now());

            Fee reservationFee = this.feeRepository.findById(reservation.getFeeId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fee " + reservation.getFeeId() + "not found"));

            return (double) (duration.getSeconds() * reservationFee.getFeePrice() / (3600));
        }
    }

    public List<Reservation> findAllReservations() {
        return this.reservationRepository.findAllReservations();
    }

    public List<Reservation> findAllReservations(Long parkingId) {
        return this.reservationRepository.findAllReservations(parkingId);
    }

    public List<Reservation> findAllReservations(String ownerDni) {
        final Optional<User> userOptional = this.userRepository.findByDni(ownerDni);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + ownerDni + " not found");
        }
        return userOptional.get().getParkings().stream()
                .flatMap(parking -> parking.getReservations().stream())
                .collect(Collectors.toList());
    }

    public List<Reservation> findAllCurrentReservations(Long parkingId) {
        return this.reservationRepository.findAllCurrentReservations(parkingId);
    }

    public List<Fee> findAllFees(Long parkingId) {
        return this.parkingRepository.findAllFees(parkingId);
    }

    public List<Floor> findAllFloors(Long parkingId) {
        List<Floor> floors = this.parkingRepository.findAllFloors(parkingId);

        floors.forEach(floor -> {
            Long floorId = floor.getFloorId();
            int reservationsPerFloor = this.reservationRepository.findAllCurrentReservationsPerFloor(parkingId, floorId).size();
            floor.setSlotsNumber(floor.getSlotsNumber() - reservationsPerFloor);
        });
        return floors;
    }
}
