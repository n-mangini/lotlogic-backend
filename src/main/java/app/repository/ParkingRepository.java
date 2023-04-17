package app.repository;

import app.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
    Optional<Parking> findByAddress(String address);
}
