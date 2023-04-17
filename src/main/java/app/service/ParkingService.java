package app.service;

import app.model.Parking;
import app.repository.ParkingRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ParkingService {
    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    //TODO set floors and users when create
    public void saveParking(@RequestBody @NotNull Parking parking) {
        Optional<Parking> parkingOptional = this.parkingRepository.findByAddress(parking.getAddress());
        if (parkingOptional.isEmpty()) {
            this.parkingRepository.save(parking);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "parking " + parking.getAddress() + " already exists");
        }
    }
}
