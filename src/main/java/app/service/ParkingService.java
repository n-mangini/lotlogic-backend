package app.service;

import app.model.Parking;
import app.model.User;
import app.model.form.ParkingAddForm;
import app.model.form.ParkingEditForm;
import app.repository.FloorRepository;
import app.repository.ParkingRepository;
import app.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingService {
    private final ParkingRepository parkingRepository;
    private final FloorRepository floorRepository;
    private final UserRepository userRepository;

    public ParkingService(ParkingRepository parkingRepository, FloorRepository floorRepository, UserRepository userRepository) {
        this.parkingRepository = parkingRepository;
        this.floorRepository = floorRepository;
        this.userRepository = userRepository;
    }

    public void saveParking(@RequestBody @NotNull ParkingAddForm parking) {
        Optional<Parking> parkingOptional = this.parkingRepository.findByAddress(parking.getAddress());
        if (parkingOptional.isEmpty()) {
            if (parking.getDni() == null)
                throw new ResponseStatusException(HttpStatus.CONFLICT, "parking should be assigned to user");
            Parking parkingToSave = new Parking(parking.getAddress(), parking.getCarFee(), parking.getMotorbikeFee(), parking.getTruckFee());
            this.floorRepository.saveAll(parking.getFloors());
            parking.getFloors().forEach(floor -> parkingToSave.getFloors().add(floor));
            this.parkingRepository.save(parkingToSave);
            assignParkingToUser(parking.getDni(), parkingToSave);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "parking " + parking.getAddress() + " already exists");
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

    //TODO modify floors and slots
    public void updateParking(@NotNull Long parkingId, @NotNull ParkingEditForm parkingEditForm) {
        final Optional<Parking> parkingOptional = this.parkingRepository.findById(parkingId);
        if (parkingOptional.isPresent()) {
            Parking parkingById = parkingOptional.get();
            parkingById.setAddress(parkingEditForm.getAddress());
            parkingById.setCarFee(parkingEditForm.getCarFee());
            parkingById.setMotorbikeFee(parkingEditForm.getMotorbikeFee());
            parkingById.setTruckFee(parkingEditForm.getTruckFee());
            this.parkingRepository.save(parkingById);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "parking " + parkingId + " not found");
        }
    }

    public List<Parking> getAllParkings(){
        return this.parkingRepository.findAllParkings();
    }
}
