package app.service;

import app.model.Fee;
import app.model.Floor;
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
    private final FeeRepository feeRepository;
    private final FloorRepository floorRepository;
    private final UserRepository userRepository;

    public ParkingService(ParkingRepository parkingRepository, FeeRepository feeRepository, FloorRepository floorRepository, UserRepository userRepository) {
        this.parkingRepository = parkingRepository;
        this.feeRepository = feeRepository;
        this.floorRepository = floorRepository;
        this.userRepository = userRepository;
    }

    public void saveParking(@RequestBody @NotNull ParkingAddForm parkingAddForm) {
        Optional<Parking> parkingOptional = this.parkingRepository.findByAddress(parkingAddForm.getAddress());
        if (parkingOptional.isEmpty() || !parkingOptional.get().isActive()) {
            if (parkingAddForm.getDni() == null)
                throw new ResponseStatusException(HttpStatus.CONFLICT, "parking should be assigned to user");
            Parking parkingToSave = new Parking(parkingAddForm.getAddress());

            //add floor
            List<Floor> floors = parkingToSave.getFloors();
            this.floorRepository.saveAll(parkingAddForm.getFloors());
            parkingAddForm.getFloors().forEach(floor -> parkingToSave.getFloors().add(floor));

            //add fee
            List<Fee> fees = parkingAddForm.getFees();
            this.feeRepository.saveAll(fees);
            parkingAddForm.getFees().forEach(fee -> parkingToSave.getFees().add(fee));

            this.parkingRepository.save(parkingToSave);
            assignParkingToUser(parkingAddForm.getDni(), parkingToSave);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "parking " + parkingAddForm.getAddress() + " already exists");
        }
    }

    private void assignParkingToUser(String dni, Parking parking) {
        final Optional<User> userOptional = this.userRepository.findByDni(dni);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + dni + " not found");
        } else {
            User user = userOptional.get();
            user.getParkings().add(parking);
            this.userRepository.save(user);
        }
    }

    public void deleteParking(Long parkingId) {
        final Optional<Parking> parkingOptional = this.parkingRepository.findById(parkingId);
        if (parkingOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "parking " + parkingId + " not found");
        } else {
            Parking parkingById = parkingOptional.get();
            parkingById.setActive(false);
            this.parkingRepository.save(parkingById);
        }
    }

    //TODO modify floors, slots and fees [modify arrays with json]
    public void updateParking(@NotNull Long parkingId, @NotNull ParkingEditForm parkingEditForm) {
        final Optional<Parking> parkingOptional = this.parkingRepository.findById(parkingId);
        if (parkingOptional.isPresent()) {
            Parking parkingById = parkingOptional.get();
            parkingById.setAddress(parkingEditForm.getAddress());
            parkingById.setFloors(parkingEditForm.getFloors());
            parkingById.setFees(parkingEditForm.getFees());
            this.parkingRepository.save(parkingById);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "parking " + parkingId + " not found");
        }
    }

    public List<Parking> getAllParkings() {
        return this.parkingRepository.findAllParkings();
    }
}
