package app.service;

import app.model.Parking;
import app.model.User;
import app.model.form.ParkingAddForm;
import app.model.form.ParkingEditForm;
import app.repository.FeeRepository;
import app.repository.FloorRepository;
import app.repository.ParkingRepository;
import app.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class ParkingService {
    private final ParkingRepository parkingRepository;
    private final UserRepository userRepository;

    public ParkingService(ParkingRepository parkingRepository, UserRepository userRepository) {
        this.parkingRepository = parkingRepository;
        this.userRepository = userRepository;
    }

    public void saveParking(@RequestBody @NotNull ParkingAddForm parkingAddForm) {
        Optional<Parking> parkingOptional = this.parkingRepository.findByAddress(parkingAddForm.getAddress());
        if (parkingOptional.isPresent() && parkingOptional.get().isActive())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "parking " + parkingAddForm.getAddress() + " already exists");
        if (parkingAddForm.getDni() == null)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "parking should be created by an user");
        final Optional<User> userOptional = this.userRepository.findByDni(parkingAddForm.getDni());
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + parkingAddForm.getDni() + " not found");
        } else {
            User user = userOptional.get();
            Parking parking = new Parking(parkingAddForm.getAddress(), parkingAddForm.getFloors(), parkingAddForm.getFees());
            user.getParkings().add(parking);
            this.userRepository.save(user);
        }
    }

    public void deleteParking(Long parkingId) {
        final Optional<Parking> parkingOptional = this.parkingRepository.findById(parkingId);
        if (parkingOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "parking " + parkingId + " not found");
        }
        Parking parkingById = parkingOptional.get();
        parkingById.setActive(false);
        this.parkingRepository.save(parkingById);

    }

    //TODO check if parking is updated only by the owner or Admin
    public void updateParking(@NotNull Long parkingId, @NotNull ParkingEditForm parkingEditForm) {
        final Optional<User> userOptional = this.userRepository.findByDni(parkingEditForm.getDni());
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + parkingId + " not found");
        }
        User user = userOptional.get();
        Optional<Parking> parkingOptional = user.getParkings().stream()
                .filter(parking -> parking.getId() == parkingId)
                .findFirst();
        if (parkingOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "parking " + parkingId + " not found");
        }
        Parking parkingById = parkingOptional.get();
        parkingById.setAddress(parkingEditForm.getAddress());
        parkingById.setFloors(parkingEditForm.getFloors());
        parkingById.setFees(parkingEditForm.getFees());
        this.parkingRepository.save(parkingById);
    }

    public List<Parking> getAllParkings() {
        return this.parkingRepository.findAllParkings();
    }
}
