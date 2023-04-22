package app.service;

import app.model.Parking;
import app.model.Reservation;
import app.model.dto.ReservationAddForm;
import app.repository.ParkingRepository;
import app.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        parking.getReservations().add(new Reservation(reservationAddForm.carPlate(), reservationAddForm.carModel(), reservationAddForm.carType(), reservationAddForm.entryDate(), reservationAddForm.exitDate()));
        this.parkingRepository.save(parking);
    }

    public List<Reservation> findAllReservations(Long parkingId) {
        return this.reservationRepository.findAllReservations(parkingId);
    }
}
