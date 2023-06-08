package app.service;

import app.model.Parking;
import app.model.User;
import app.model.dto.ParkingAddForm;
import app.model.dto.ParkingEditForm;
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

    public Long saveParking(@RequestBody @NotNull ParkingAddForm parkingAddForm) {
        Optional<Parking> parkingOptional = this.parkingRepository.findByAddress(parkingAddForm.address());
        if (parkingOptional.isPresent() && parkingOptional.get().isActive())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "parking " + parkingAddForm.address() + " already exists");
        if (parkingAddForm.dni() == null)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "parking should be created by an user");
        final Optional<User> userOptional = this.userRepository.findByDni(parkingAddForm.dni());
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + parkingAddForm.dni() + " not found");
        } else {
            User user = userOptional.get();
            Parking parking = new Parking(parkingAddForm.address(), parkingAddForm.floors(), parkingAddForm.fees());
            user.getParkings().add(parking);
            this.userRepository.save(user);
            //parking id from parking added
            return user.getParkings().get(user.getParkings().size() - 1).getId();
        }
    }

    //TODO check if parking is deleted only by the owner or ADMIN
    public void deleteParking(Long parkingId) {
        final Optional<Parking> parkingOptional = this.parkingRepository.findById(parkingId);
        if (parkingOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "parking " + parkingId + " not found");
        }
        Parking parkingById = parkingOptional.get();
        parkingById.setActive(false);
        this.parkingRepository.save(parkingById);

    }

    //TODO check if parking is updated only by the owner or ADMIN
    public void updateParking(@NotNull Long parkingId, @NotNull ParkingEditForm parkingEditForm) {
        final Optional<User> userOptional = this.userRepository.findByDni(parkingEditForm.dni());
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
        parkingById.setAddress(parkingEditForm.address());
        parkingById.setFloors(parkingEditForm.floors());
        parkingById.setFees(parkingEditForm.fees());
        this.parkingRepository.save(parkingById);
    }

    public Parking getParkingById(Long parkingId) {
        Optional<Parking> parkingOptional = this.parkingRepository.findById(parkingId);
        if (parkingOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "parking " + parkingId + " not found");
        }
        return parkingOptional.get();
    }

    public List<Parking> getAllParkings(Long userId) {
        Optional<User> userOptional = this.userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + userId + " not found");
        }
        User user = userOptional.get();
        return user.getParkings();
    }

    public List<Parking> getAllParkingsOfEmployee(Long employeeId) {
        return this.parkingRepository.findAllParkingsByEmployee(employeeId);
    }

    //only for admin
    public List<Parking> getAllParkings() {
        return this.parkingRepository.findAllParkings();
    }
}
