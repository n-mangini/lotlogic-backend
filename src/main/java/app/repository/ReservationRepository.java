package app.repository;

import app.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r")
    List<Reservation> findAllReservations();

    @Query("SELECT r FROM Reservation r WHERE r.parking.id = ?1")
    List<Reservation> findAllReservations(Long parkingId);

    @Query("SELECT r FROM Reservation r WHERE r.parking.id = ?1 AND r.exitDate = NULL")
    List<Reservation> findAllCurrentReservations(Long parkingId);
}
