package app.repository;

import app.model.Parking;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
    @NotNull Optional<Parking> findById(@NotNull Long parkingId);

    Optional<Parking> findByAddress(String address);

    @Query("SELECT p FROM Parking p WHERE p.active = true")
    List<Parking> findAllParkings();
}
